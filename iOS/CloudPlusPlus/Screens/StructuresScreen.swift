//
//  StructuresScreen.swift
//  CloudPlusPlus
//
//  Created by arthur gonzaga on 18/05/25.
//

import SwiftUI
import SwiftData

struct StructuresScreen: View {
    
    @Query var structures: [FormStructure]
    
    var body: some View {
        NavigationView {
            List {
                ForEach(structures) { structure in
                    NavigationLink(destination: FormScreen(structure: structure)) {
                        VStack(alignment: .leading) {
                            Text(structure.name)
                                .font(.headline)
                        }
                    }
                }
            }
            .navigationTitle("Structures")
        }
    }
}

#Preview {
    StructuresScreen()
}
