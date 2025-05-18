//
//  HtmlText.swift
//  CloudPlusPlus
//
//  Created by arthur gonzaga on 18/05/25.
//

import SwiftUI
import UIKit

struct HTMLText: UIViewRepresentable {
    let html: String

    func makeUIView(context: Context) -> UILabel {
        let label = UILabel()
        label.numberOfLines = 0
        label.setContentCompressionResistancePriority(.defaultLow, for: .horizontal)
        return label
    }

    func updateUIView(_ uiView: UILabel, context: Context) {
        guard let data = html.data(using: .utf8) else {
            uiView.text = html
            return
        }

        let options: [NSAttributedString.DocumentReadingOptionKey: Any] = [
            .documentType: NSAttributedString.DocumentType.html,
            .characterEncoding: String.Encoding.utf8.rawValue
        ]

        if let attributedString = try? NSAttributedString(data: data, options: options, documentAttributes: nil) {
            uiView.attributedText = attributedString
        } else {
            uiView.text = html
        }
    }
}
