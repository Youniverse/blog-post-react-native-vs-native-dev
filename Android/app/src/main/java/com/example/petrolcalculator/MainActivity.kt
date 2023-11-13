package com.example.petrolcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.petrolcalculator.ui.theme.PetrolCalculatorTheme
import com.farimarwat.speedmeter.SpeedMeter


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PetrolCalculatorTheme {
                // A surface container using the 'background' color from the theme
                FuelEfficiencyCalculator()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FuelEfficiencyCalculator() {

    var screenStateCalcuationReady by remember { mutableStateOf(false) }

    var distance by remember { mutableStateOf(TextFieldValue()) }
    var gasoline by remember { mutableStateOf(TextFieldValue()) }
    var price    by remember { mutableStateOf(TextFieldValue()) }

    var fuelEfficiency by remember { mutableStateOf(0.0) }
    var costForDistance by remember { mutableStateOf(0.0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text("Benzinpreisrechner",
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = distance,
            onValueChange = { distance = it },
            label = { Text("Gefahrene Distanz [km]:", fontFamily = FontFamily.Serif) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        OutlinedTextField(
            value = gasoline,
            onValueChange = { gasoline = it },
            label = { Text("Getankte Menge [l]:", fontFamily = FontFamily.Serif) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Preis/Liter [EUR]:", fontFamily = FontFamily.Serif) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                // Calculate the fuel efficiency
                val distanceValue = distance.text.toDoubleOrNull()
                val gasolineValue = gasoline.text.toDoubleOrNull()
                val priceValue    = price.text.toDoubleOrNull()

                if (distanceValue != null && gasolineValue != null && priceValue != null) {
                    screenStateCalcuationReady = true
                    fuelEfficiency = (gasolineValue / distanceValue) * 100

                    val totalGasolinePrice = gasolineValue * priceValue
                    costForDistance = (totalGasolinePrice / distanceValue) * 100
                }
            }
        ) {
            Text("Berechnen")
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (screenStateCalcuationReady) {
            Text(
                text = "Verbrauch: %.2f l/100km".format(fuelEfficiency),
                fontFamily = FontFamily.Serif
            )
            Text(
                text = "Kosten: %.2f EUR/100km".format(costForDistance),
                fontFamily = FontFamily.Serif
            )
            Spacer(modifier = Modifier.height(16.dp))
            SpeedMeter(
                modifier = Modifier.size(200.dp, 200.dp),
                backgroundColor = Color.Black,
                progressWidth = 16f,
                progress =  (fuelEfficiency.toFloat()),
                needleColors = listOf(Color.Black,Color.White),
                needleKnobColors = listOf(Color.Black,Color.Gray),
                needleKnobSize = 10f,
                progressColors = listOf(Color.Green, Color.Yellow, Color.Red),
                unitText = "l"
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewFuelEfficiencyCalculator() {
    PetrolCalculatorTheme() {
        FuelEfficiencyCalculator()
    }
}