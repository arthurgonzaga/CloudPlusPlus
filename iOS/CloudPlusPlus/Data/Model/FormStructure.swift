//
//  FormStructure.swift
//  CloudPlusPlus
//
//  Created by arthur gonzaga on 18/05/25.
//
import SwiftData

@Model
class FormStructure {
    var id: String
    var name: String
    var totalFields: Int
    
    init(id: String, name: String, totalFields: Int) {
        self.id = id
        self.name = name
        self.totalFields = totalFields
    }
}
