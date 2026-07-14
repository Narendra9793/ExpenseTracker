
import React from 'react';
import {NavigationContainer} from '@react-navigation/native'
import {createNativeStackNavigator} from '@react-navigation/native-stack'
import Login from './src/app/pages/login';
import SignUp from './src/app/pages/signup';
import Home from './src/app/pages/Home';

const Stack= createNativeStackNavigator()

function App() {
  

  return (
    <NavigationContainer >
      <Stack.Navigator>
        <Stack.Screen name="Login" component={Login}/>
        <Stack.Screen name="Signup" component={SignUp}/>
        <Stack.Screen name="Home" component={Home}/>
      </Stack.Navigator>
    </NavigationContainer>
  );
}



export default App;