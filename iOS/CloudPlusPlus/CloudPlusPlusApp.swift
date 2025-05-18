//
//  CloudPlusPlusApp.swift
//  CloudPlusPlus
//
//  Created by arthur gonzaga on 18/05/25.
//

import SwiftUI
import SwiftData

@main
struct CloudPlusPlusApp: App {
    
    var body: some Scene {
        WindowGroup {
            StructuresScreen()
        }
        .modelContainer(for: [FormStructure.self, FormData.self, FormSection.self, FormFieldValue.self])
    }
}
