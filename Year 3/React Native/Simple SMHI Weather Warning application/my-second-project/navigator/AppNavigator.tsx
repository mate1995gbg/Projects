import React from 'react';
import { createStackNavigator } from '@react-navigation/stack';
import Home from '../components/Home'; //home component
import WeatherWarnings from '../components/WeatherWarnings';

const Stack = createStackNavigator();  //function that returns an object containing React components and functions that we use to configure our navigator

export default function AppNavigator() {
    return (
        <Stack.Navigator initialRouteName='Home'> 
            <Stack.Screen 
                name="Home" //Stack.navigator & Screen are components used to configure the navigator and define screens & routes.
                component={Home}
                options={{title: 'Welcome'}}
            />
            <Stack.Screen 
                name="WeatherWarnings"
                component={WeatherWarnings}
                options={{title: 'Current Weather Warnings'}}
            />
        </Stack.Navigator>
    )
}