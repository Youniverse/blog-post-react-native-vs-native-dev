//
//  ContentView.swift
//  PetrolCalculator
//
//  Created by Gunnar Matz on 18.08.23.
//

import SwiftUI
import ABGaugeViewKit

struct ContentView: View {
    @State private var distance = ""
    @State private var fuelAmount = ""
    @State private var price = ""
    
    @State var fuelEfficiencyText = "-"
    @State var costEfficiencyText = "-"
    
    @State var gaugeNeedleValue = 0.0
    
    var body: some View {
        VStack(spacing: 20) {
            Label("Benzinpreisrechner", systemImage: "book.fill")
                .labelStyle(TitleOnlyLabelStyle())
                .font(.system(size: 26.0))
                
            TextField("Gefahrene Distanz [km]", text: $distance).keyboardType(.decimalPad).multilineTextAlignment(.center)
            TextField("Getankte Menge [l]", text: $fuelAmount).keyboardType(.decimalPad).multilineTextAlignment(.center)
            TextField("Preis/Liter [EUR]", text: $price).keyboardType(.decimalPad).multilineTextAlignment(.center)
            
            Button("Berechnen") {
                calculateFuelEfficiency()
            }
            
            Text(fuelEfficiencyText).labelStyle(TitleOnlyLabelStyle())
            Text(costEfficiencyText).labelStyle(TitleOnlyLabelStyle())
            
            TachometerView(gaugeNeedleValue: $gaugeNeedleValue)
                .frame(minWidth: 150, maxWidth: 150, minHeight: 150, maxHeight: 150)
        }
        .padding()
    }
    
    private func calculateFuelEfficiency() {
        let numberFormatter = NumberFormatter()
        numberFormatter.minimumFractionDigits = 2
        numberFormatter.maximumFractionDigits = 2
        numberFormatter.decimalSeparator = ","

        if let distance = Double(distance),
           let gasolineUsed = Double(fuelAmount),
           let pricePerLiter = Double(price){
            
            let fuelEfficiency = (gasolineUsed / distance) * 100
            if let formattedFuelEfficiency = numberFormatter.string(from: NSNumber(value: fuelEfficiency)) {
                fuelEfficiencyText = "Verbrauch: \(formattedFuelEfficiency) l/100km"
                
                gaugeNeedleValue = fuelEfficiency * 6
            }
            
            let totalGasolinePrice = gasolineUsed * pricePerLiter
            let costForDistance = (totalGasolinePrice / distance) * 100
            if let formattedCost = numberFormatter.string(from: NSNumber(value: costForDistance)) {
                costEfficiencyText = "Kosten: \(formattedCost) EUR/100km"
            }
        }
        
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

struct TachometerView: UIViewRepresentable {
    @Binding var gaugeNeedleValue: Double

    func makeUIView(context: Context) -> UIView {
        let gauge = ABGaugeView()
        gauge.colorCodes = "00ff00,ffA500,ff0000"
        gauge.areas = "30, 30, 40"
        gauge.needleValue = 0
        return gauge
    }

    func updateUIView(_ uiView: UIView, context: Context) {
        (uiView as! ABGaugeView).needleValue = gaugeNeedleValue
    }
}
