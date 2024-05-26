import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import AppNavigator from './navigator/AppNavigator'; // Importing our navigator component

export default function App() {
  return (
      <NavigationContainer>
        <AppNavigator/>
      </NavigationContainer>
  );
}

