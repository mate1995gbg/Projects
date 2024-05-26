import React from 'react';
import { Button, StyleSheet, Text, TextInput, View } from 'react-native';

export default function Home({navigation}) {
  return (
    <View style={styles.TopView}>
        <Text>
          Welcome!
        </Text>
      <View style={styles.MiddleView}>
        <Text>
        This app allows you to see any current warnings issued by the swedish SMHI (Swedish Meteorological and hydrological Institute), via an API.
        </Text>
      </View>
      <View style={styles.BottomView}>
        <Button 
        title='See Warnings'
        onPress={() => navigation.navigate('WeatherWarnings')}
        />
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  TopView:{
    padding:  50
  },
  MiddleView:{
    marginTop: 50,
    flexDirection: 'column', 
    justifyContent: 'center'
  },
  BottomView:{
    marginTop: 50,
    flexDirection: 'column', 
    justifyContent: 'center'
  },
  TextInputStyle:{
    width: '80%', 
    borderColor: 'black' , 
    borderWidth: 1
  }
});