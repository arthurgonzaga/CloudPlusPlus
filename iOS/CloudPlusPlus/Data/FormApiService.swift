//
//  FormApiService.swift
//  CloudPlusPlus
//
//  Created by arthur gonzaga on 18/05/25.
//

import Foundation

final class FormApiService {
    
    static let shared: FormApiService = FormApiService()
    
    private init() {}
}

extension FormApiService {
    
    func getStructures() -> [String] {
        return [
            readJson(name: "all-fields"),
            readJson(name: "200-form")
        ]
    }
    
    private func readJson(name: String) -> String {
        let url = Bundle.main.url(forResource: name, withExtension: "json")!
        return try! String(data: Data(contentsOf: url), encoding: .utf8)!
    }
}
