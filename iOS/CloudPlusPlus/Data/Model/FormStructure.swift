//
//  FormStructure.swift
//  CloudPlusPlus
//
//  Created by arthur gonzaga on 18/05/25.
//
import SwiftUI
import SwiftData

@Model
class FormStructure {
    var id: String
    var name: String
    var totalFields: Int
    
    @Transient
    var backgroundColor: Color {
        if totalFields < 100 {
            Color(hex: 0xCCF5EE)
        } else if totalFields >= 200 && totalFields <= 500 {
            Color(hex: 0xFFF9CC)
        } else {
            Color(hex: 0xFFE0E0)
        }
    }
    
    @Transient
    var textColor: Color {
        if totalFields < 100 {
            Color(hex: 0x004D40)
        } else if totalFields >= 200 && totalFields <= 500 {
            Color(hex: 0x726B32)
        } else {
            Color(hex: 0x885555)
        }
    }
    
    init(id: String, name: String, totalFields: Int) {
        self.id = id
        self.name = name
        self.totalFields = totalFields
    }
}
