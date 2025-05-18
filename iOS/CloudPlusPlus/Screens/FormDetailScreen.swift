//
//  FormDetailScreen.swift
//  CloudPlusPlus
//
//  Created by arthur gonzaga on 18/05/25.
//

import SwiftUI
import SwiftData

struct FormDetailScreen: View {
    
    @Environment(\.modelContext) var modelContext
    @Environment(\.dismiss) var dismiss
    
    private let formId: String
    private let structure: FormStructure
    @State var totalSections: Int = 0
    
    @State var currentSectionId: String = ""
    @State var currentSection: Int = 0
    @State var items: [Item] = []
    
    init(formId: String, structure: FormStructure) {
        self.formId = formId
        self.structure = structure
    }
    
    var body: some View {
        ScrollView {
            VStack(alignment: .leading) {
                
                let section = currentSection+1
                VStack(spacing: 8) {
                    ProgressView(value: Float(section), total: Float(totalSections))
                        .progressViewStyle(LinearProgressViewStyle(tint: structure.textColor))
                        .background(structure.backgroundColor)
                        .frame(maxWidth: .infinity)
                        .padding(.horizontal, 42)

                    Text("Section \(section) of \(totalSections)")
                        .font(.footnote)
                }
                
                ForEach(items) { item in
                    itemView(item)
                        .padding(.top, 16)
                        .padding(.horizontal, 20)
                }
                
                HStack(spacing: 12) {
                    if section > 1 && section < totalSections {
                        Button(action: {
                            getSection(index: currentSection-1)
                        }) {
                            Text("Back")
                                .frame(maxWidth: .infinity)
                        }
                        .buttonStyle(.bordered)
                    }

                    Button(action: {
                        if section < totalSections {
                            getSection(index: currentSection+1)
                        } else {
                            dismiss()
                        }
                    }) {
                        Text(section < totalSections ? "Next" : "Finish")
                            .frame(maxWidth: .infinity)
                    }
                    .buttonStyle(.borderedProminent)
                }
                .padding(.horizontal)
            }
        }
        .navigationTitle(structure.name)
        .task {
            await MainActor.run {
                let structureId = structure.id
                totalSections = try! modelContext.fetchCount(FetchDescriptor<FormSection>(
                    predicate: #Predicate {
                        $0.structureId == structureId
                    }
                ))
                getSection(index: 0)
            }
        }
        .accentColor(structure.textColor)
    }
    
    @ViewBuilder
    func itemView(_ item: Item) -> some View {
        switch (item.field) {
        case .textField(let data):
            textField(id: data.uuid, label: data.label, value: item.value)
        case .number(let data):
            textField(id: data.uuid, label: data.label, value: item.value)
                .keyboardType(.decimalPad)
        case .description(let data):
            HTMLText(html: data.label)
                .font(.subheadline)
                .foregroundColor(.secondary)
        case .dropdown(let data):
            Picker(data.label, selection: Binding<String>(
                get: {
                    data.options.first(where: {$0.value == item.value })?.value ?? data.options.first?.value ?? ""
                },
                set: { value in
                    let index = items.firstIndex(where: { $0.field.id == item.field.id })!
                    items[index].value = value
                    onValueChange(id: items[index].id, value: value)
                }
            )) {
                ForEach(data.options) { option in
                    VStack {
                        Text(option.label)
                            .tag(item.value)
                    }
                }
            }
            .pickerStyle(MenuPickerStyle())
        }
    }

    @ViewBuilder
    func textField(id: String, label: String, value: String) -> some View {
        TextField(
            label,
            text: Binding(
                get: { value },
                set: { value in
                    if let index = items.firstIndex(where: { $0.field.id == id }) {
                        items[index].value = value
                        onValueChange(id: id, value: value)
                    }
                }
            )
        )
        .textFieldStyle(.roundedBorder)
    }
}

extension FormDetailScreen {
    
    struct Item: Identifiable {
        var id: String {
            field.id
        }
        let field: Field
        var value: String
    }
    
    func getSection(index: Int) {
        guard index < totalSections else { return }
        
        // Fetch section
        let structureId = structure.id
        var descriptor = FetchDescriptor<FormSection>(
            predicate: #Predicate {
                $0.structureId == structureId &&
                $0.index == index
            }
        )
        descriptor.fetchLimit = 1
        let section = try! modelContext.fetch(descriptor).first!
        
        // Fetch values of the section
        let sectionId = section.id
        let fieldValues = try! modelContext.fetch(
            FetchDescriptor<FormFieldValue>(
                predicate: #Predicate {
                    $0.formId == formId &&
                    $0.sectionId == sectionId
                }
            )
        )
        
        currentSection = index
        currentSectionId = sectionId
        
        let fieldsList = try! JSONDecoder().decode([Field].self, from: section.fields.data(using: .utf8)!)
        items = fieldsList.map { field in
            let value = fieldValues.first(where: { $0.id == field.id })?.value ?? ""
            return Item(field: field, value: value)
        }
    }
    
    func onValueChange(id: String, value: String) {
        var descriptor = FetchDescriptor<FormFieldValue>(
            predicate: #Predicate {
                $0.id == id &&
                $0.formId == formId &&
                $0.sectionId == currentSectionId
            }
        )
        descriptor.fetchLimit = 1

        if let existingFieldValue: FormFieldValue = try? modelContext.fetch(descriptor).first {
            existingFieldValue.value = value
        } else {
            let newUser = FormFieldValue(id: id, formId: formId, sectionId: currentSectionId, value: value)
            modelContext.insert(newUser)
        }
    }
}
