//
//  StructuresScreen.swift
//  CloudPlusPlus
//
//  Created by arthur gonzaga on 18/05/25.
//

import SwiftUI
import SwiftData
import SwiftUI
import SwiftData

struct StructuresScreen: View {
    
    @Environment(\.modelContext) var modelContext
    @Query(sort: \FormStructure.totalFields) var structures: [FormStructure]
    
    @State var structureSelected: FormStructure?

    var body: some View {
        NavigationStack {
            ScrollView {
                VStack(alignment: .leading) {
                    
                    Spacer().frame(height: 62)

                    HStack {
                        Image("logo")
                            .resizable()
                            .frame(width: 150, height: 32)
                        
                        Spacer()

                        Circle()
                            .fill(Color.gray.opacity(0.2))
                            .frame(width: 40, height: 40)
                    }
                    .frame(height: 40)
                    .padding(.horizontal, 32)

                    Spacer().frame(height: 32)

                    // Category chips
                    Text("Category")
                        .font(.headline)
                        .fontWeight(.semibold)
                        .padding(.horizontal, 32)
                    
                    ScrollView(.horizontal, showsIndicators: false) {
                        HStack(spacing: 8) {
                            ForEach(0..<10) { _ in
                                Spacer()
                                    .padding(.horizontal, 12)
                                    .frame(width: 100, height: 40)
                                    .background(Color.gray.opacity(0.2))
                                    .cornerRadius(10)
                            }
                        }
                        .padding(.horizontal, 32)
                    }
                    

                    Spacer().frame(height: 8)
                    
                    Text("Types")
                        .font(.headline)
                        .fontWeight(.semibold)
                        .padding(.horizontal, 32)
                    
                    VStack(spacing: 12) {
                        ForEach(structures) { structure in
                            NavigationLink(value: structure) {
                                card(structure: structure)
                            }
                            .buttonStyle(PlainButtonStyle())
                            .simultaneousGesture(TapGesture().onEnded {
                                structureSelected = structure
                            })
                            .buttonStyle(PlainButtonStyle())
                        }
                    }
                    .padding(.horizontal, 32)
                }
                .padding(.vertical)
            }
            .navigationDestination(for: FormStructure.self) { structure in
                FormScreen(structure: structure)
            }
            .onAppear {
                getStructuresIfEmpty()
            }
        }
        .accentColor(structureSelected?.textColor ?? .blue)
    }
    
    @ViewBuilder
    private func card(structure: FormStructure) -> some View {
        VStack(alignment: .leading, spacing: 4) {
            HStack(alignment: .top) {
                Text(structure.name)
                    .fontWeight(.bold)
                    .multilineTextAlignment(.leading)
                    .foregroundColor(structure.textColor)

                Spacer()

                Text("\(structure.totalFields) fields")
                    .font(.subheadline)
                    .foregroundColor(structure.textColor)
            }

            Text(structure.id)
                .font(.caption)
                .foregroundColor(structure.textColor)
        }
        .padding()
        .background(structure.backgroundColor)
        .cornerRadius(20)
    }
}

extension StructuresScreen {
    
    func getStructuresIfEmpty() {
        guard structures.isEmpty else { return }
        
        let stringList = FormApiService.shared.getStructures()
        let structureList = Mapper.getFormStructureList(structureStringList: stringList)
        
        for (index, json) in stringList.enumerated() {
            let formStructure = structureList[index]
            let sections = Mapper.getFormSectionList(json: json, structureId: formStructure.id)
            
            modelContext.insert(formStructure)
            for section in sections {
                modelContext.insert(section)
            }
        }
        try? modelContext.save()
    }
}
