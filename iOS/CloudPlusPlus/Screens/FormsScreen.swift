//
//  FormsScreen.swift
//  CloudPlusPlus
//
//  Created by arthur gonzaga on 18/05/25.
//
import SwiftUI
import SwiftData

struct FormScreen: View {
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
        NavigationView {
            List {
                ForEach(forms) { form in
                    NavigationLink(destination: FormDetailScreen(formId: form.id, structure: structure)) {
                        VStack(alignment: .leading) {
                            Text(form.id)
                                .font(.headline)
                        }
                    }
                }
            }
            .navigationTitle("Structures")
        }
    }
}


