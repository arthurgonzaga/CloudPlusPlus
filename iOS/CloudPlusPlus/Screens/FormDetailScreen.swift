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
    
    private let formId: String
    private let structure: FormStructure
    
    @State var currentSection: Int = 0
    @State var items: [Item] = []
    
    init(formId: String, structure: FormStructure) {
        self.formId = formId
        self.structure = structure
    }
    
    var body: some View {
        Form {
            ForEach(items) { item in
                itemView(item)
            }
        }
        .onAppear {
            getSection(index: 0)
        }
    }
    
    @ViewBuilder
    func itemView(_ item: Item) -> some View {
        switch (item.field) {
        case .textField(let data):
            textField(id: data.id, label: data.label, value: item.value)
        case .number(let data):
            textField(id: data.id, label: data.label, value: item.value)
                .keyboardType(.decimalPad)
        case .description(let data):
            Text(data.label)
                .font(.subheadline)
                .foregroundColor(.secondary)
        case .dropdown(let data):
            Picker(data.label, selection: Binding(
                get: {
                    data.options.first(where: {$0.value == item.value })?.label ?? ""
                },
                set: { value in
                    let index = items.firstIndex(where: { $0.field.id == item.field.id })!
                    items[index].value = value
                }
            )) {
                ForEach(data.options) { option in
                    Text(option.label).tag(option.value)
                }
            }
        }
    }

    @ViewBuilder
    func textField(id: String, label: String, value: String) -> some View {
        TextField(
            label,
            text: Binding(
                get: { value },
                set: { value in
                    let index = items.firstIndex(where: { $0.field.id == id })!
                    items[index].value = value
                }
            )
        )
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
        
        
        let fieldsList = try! JSONDecoder().decode([Field].self, from: section.fields.data(using: .utf8)!)
        items = fieldsList.map { field in
            let value = fieldValues.first(where: { $0.id == field.id })?.value ?? ""
            return Item(field: field, value: value)
        }
    }
    
}
