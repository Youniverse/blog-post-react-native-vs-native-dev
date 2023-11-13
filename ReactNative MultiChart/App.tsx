import React, {useState} from 'react';
import {View, TextInput, Button, StyleSheet, Text, ScrollView} from 'react-native';
import RNSpeedometer from 'react-native-speedometer'

const App = () => {
    const [distance, setDistance] = useState('');
    const [fuelAmount, setFuelAmount] = useState('');
    const [price, setPrice] = useState('');

    const [fuelEfficiency, setFuelEfficiency] = useState(0);
    const [costEfficiency, setCostEfficiency] = useState(0);



    const calculateFuelEfficiency = () => {
        const distanceValue = parseFloat(distance);
        const fuelAmountValue = parseFloat(fuelAmount);
        const priceValue = parseFloat(price);

        if (!isNaN(distanceValue) && !isNaN(fuelAmountValue) && !isNaN(priceValue)) {
            const efficiency = (fuelAmountValue / distanceValue) * 100;
            setFuelEfficiency(efficiency);

            let totalGasolinePrice = fuelAmountValue * priceValue
            let costForDistance = (totalGasolinePrice / distanceValue) * 100
            setCostEfficiency(costForDistance);

        }
    };


    let speedometers = [];
    for (let i = 0; i < 100; i++) {
        speedometers.push(<RNSpeedometer value={fuelEfficiency} minValue={0} maxValue={15} labels={[
            {
                name: 'Gut',
                labelColor: '#00ff00',
                activeBarColor: '#00ff00',
            },
            {
                name: 'Mittel',
                labelColor: '#ffA500',
                activeBarColor: '#ffA500',
            },
            {
                name: 'Schlecht',
                labelColor: '#ff0000',
                activeBarColor: '#ff0000',
            }
        ]} size={200}/>)
    }


    return (
        <ScrollView> 
            <Text style={styles.headline}>
                Benzinpreisrechner
            </Text>
            <TextInput
                style={styles.input}
                placeholder="Gefahrene Distanz [km]"
                keyboardType="numeric"
                value={distance}
                onChangeText={setDistance}
            />
            <TextInput
                style={styles.input}
                placeholder="Getankte Menge [l]"
                keyboardType="numeric"
                value={fuelAmount}
                onChangeText={setFuelAmount}
            />
            <TextInput
                style={styles.input}
                placeholder="Preis/Liter [EUR]"
                keyboardType="numeric"
                value={price}
                onChangeText={setPrice}
            />
            <Button title="Berechnen" onPress={calculateFuelEfficiency}/>

            <Text style={styles.resultText}>
                Verbrauch: {Number(fuelEfficiency).toFixed(2)} l/100km
            </Text>
            <Text>
                Kosten: {Number(costEfficiency).toFixed(2)} EUR/100km
            </Text>

            <RNSpeedometer value={fuelEfficiency} minValue={0} maxValue={15} labels={[
                {
                    name: 'Gut',
                    labelColor: '#00ff00',
                    activeBarColor: '#00ff00',
                },
                {
                    name: 'Mittel',
                    labelColor: '#ffA500',
                    activeBarColor: '#ffA500',
                },
                {
                    name: 'Schlecht',
                    labelColor: '#ff0000',
                    activeBarColor: '#ff0000',
                }
            ]} size={200}/>

            {speedometers}

        </ScrollView>
    );
};


const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        padding: 20,
    },
    headline: {
        fontSize: 26,
        padding: 20,
    },
    input: {
        width: 200,
        height: 40,
        borderWidth: 1,
        borderColor: 'gray',
        marginBottom: 10,
        paddingHorizontal: 10,
    },
    resultText: {
        padding: 20
    }
});

export default App;

