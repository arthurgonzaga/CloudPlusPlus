//
//  Forms.swift
//  CloudPlusPlus
//
//  Created by arthur gonzaga on 18/05/25.
//
import SwiftData

@Model
class FormData {
    var id: String
    var structureId: String
    
    init(id: String, structureId: String) {
        self.id = id
        self.structureId = structureId
    }
}
