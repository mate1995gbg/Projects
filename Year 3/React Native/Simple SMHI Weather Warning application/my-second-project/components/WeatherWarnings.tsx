
import React, { useState, useEffect } from 'react';

import { ScrollView, View, Text, StyleSheet, ActivityIndicator } from 'react-native';
import { Warning } from '../models/Warning';
import { Description } from '../models/Description';
import { EventDescription } from '../models/EventDescription';
import Icon from 'react-native-vector-icons/FontAwesome';
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
const WeatherWarnings: React.FC = () => {
    const [data, setData] = useState<Warning[] | null>(null);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<Error | null>(null);

    useEffect(() => {
        fetch('https://opendata-download-warnings.smhi.se/ibww/api/version/1/warning.json')
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then((data: Warning[]) => {
                console.log("API Response: ", data); 
                setData(data);
                setLoading(false);
            })
            .catch((error: Error) => {
                setError(error);
                setLoading(false);
            });
    }, []);

    return (
    <ScrollView>
        <View style={styles.container}>
            {loading && <ActivityIndicator size="large" color="#0000ff" />}
            {error && <Text>Error: {error.message}</Text>}
            {!loading && !error && (
                <> 
                    {data?.map((warning, index) => (
                        <View key={index}>
                            <Text style={styles.areaName}>{warning.event.en} </Text>
                            {warning.warningAreas.map((warningAreas, areaIndex) => (
                                <View key={areaIndex} style={styles.warningContainer}>
                                    {warningAreas.affectedAreas.map((affectedArea, affectedAreaIndex) => (
                                        <View key={affectedAreaIndex}>
                                            <Text>
                                                <Icon name='compass' size={17} color="#900">
                                                     <Text> Where? </Text>
                                                </Icon>
                                            </Text>
                                            <Text style={styles.descriptionText}>{affectedArea.en}</Text>
                                        </View>
                                    ))}
                                    {/* Check if the first description exists and display its text (component following && will be rendered if descriptions[0] exists) */}
                                    {warningAreas.descriptions[0] && (
                                        <View>
                                            <Text>
                                                <Icon name='comments' size={17} color="#900">
                                                     <Text> How? </Text>
                                                </Icon>
                                            </Text>
                                            <Text style={styles.descriptionText}>{warningAreas.descriptions[0].text.en}</Text>
                                        </View>

                                    )}
                                        <View>
                                            <Text>
                                                <Icon name='hourglass' size={16} color="#900">
                                                     <Text> When? </Text>
                                                </Icon>
                                            </Text>
                                            <Text style={styles.descriptionText}>{new Date(warningAreas.approximateStart).toISOString().split('T')[0]}</Text>
                                        </View>
        
                                    {/* Additional rendering logic */}
                                </View>
                            ))}
                        </View>
                    ))}
                </>
            )}
        </View>
    </ScrollView>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        padding: 16,
    },
    header: {
        fontSize: 24,
        fontWeight: 'bold',
    },
    warningContainer: {
        marginTop: 10,
        marginBottom: 10,
        padding: 10,
        backgroundColor: '#ffe6e6',  // light red background
        borderRadius: 5,  // rounded corners
        shadowColor: '#000',  // shadow color black
        shadowOffset: { width: 0, height: 1 },  // shadow offset
        shadowOpacity: 0.2,  // shadow opacity
        shadowRadius: 1,  // shadow blur radius
        elevation: 3,  // elevation for Android
    },
    areaName: {
        fontSize: 20,
        fontWeight: 'bold',
        marginBottom: 0
    },
    descriptionText: {
        fontSize: 14,  
        padding: 5,
        color: '#4d4d4d',  // dark gray text color
    },
    dateText: {
        fontSize: 12,  
        color: '#666',  // gray text color
    },

});

export default WeatherWarnings;