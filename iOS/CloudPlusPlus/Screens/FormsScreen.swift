//
//  FormsScreen.swift
//  CloudPlusPlus
//
//  Created by arthur gonzaga on 18/05/25.
//
import SwiftUI
import SwiftData

struct FormScreen: View {
    
    @Environment(\.modelContext) var modelContext
    
    private let structure: FormStructure
    @Query var forms: [FormData]
    
    init(structure: FormStructure) {
        self.structure = structure
        let structureId = structure.id
        _forms = Query(filter: #Predicate {
            $0.structureId == structureId
        })
    }
    
    var body: some View {
        ScrollView {
            VStack(alignment: .leading) {
                Spacer().frame(height: 32)
                ForEach(forms) { form in
                    NavigationLink(destination: FormDetailScreen(formId: form.id, structure: structure)) {
                        formItem(id: form.id)
                    }
                }
            }
            .padding(.horizontal, 32)
        }
        .navigationTitle("Forms")
        .toolbar {
            ToolbarItem(placement: .topBarTrailing) {
                Button(action: {
                    modelContext.insert(FormData(id: UUID().uuidString, structureId: structure.id))
                }) {
                    Image(systemName: "plus")
                }
                .accentColor(.black)
            }
        }
        .accentColor(structure.backgroundColor)
    }
    
    @ViewBuilder
    func formItem(id: String) -> some View {
        HStack {
            Text(id)
                .foregroundColor(structure.textColor)
                .font(.system(size: 14))
                .padding(.trailing, 16)
                .lineLimit(1)
            
            Spacer()
            
            Image(systemName: "chevron.right")
                .foregroundColor(structure.textColor)
        }
        .padding(.horizontal, 20)
        .frame(minHeight: 60)
        .background(structure.backgroundColor)
        .cornerRadius(20)
    }
    
}

