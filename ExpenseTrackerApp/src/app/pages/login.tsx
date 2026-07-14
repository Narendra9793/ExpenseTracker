import { View, StyleSheet, TextInput } from 'react-native'
import { Pressable } from 'react-native';
import React, { useEffect, useState } from 'react'
import CustomBox from '../components/CustomBox'
import CustomText from '../components/CustomText'
import AsyncStorage from '@react-native-async-storage/async-storage';

const Login = ({navigation}) => {

  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [isLogedIn, setIsLogedIn] = useState(false)

  useEffect(()=>{
    const handlelogin= async ()=>{
      const loggedIn= await isLoggedIn();
      setIsLogedIn(loggedIn)
      if(loggedIn)navigation.navigate('Home', {name: 'Home'})

      else{
        const refreshed = await refreshToken();
        setIsLogedIn(refreshed);
        if(refreshed)navigation.navigate('Home', {name: 'Home'})
      }

    }
    handlelogin();

  }, [])

  const refreshToken= async()=>{
    const refreshToken= await AsyncStorage.getItem('refreshToken');
    const response = await fetch('http://localhost:9090/auth/v1/refreshToken',{
      method:'POST',
      headers:{
        Accept:'application/json',
        'Content-Type':'application/json',
        'X-Requested-With':'XMLHttpRequest'
  
      },
      body: JSON.stringify({
        token:refreshToken
      }),
    });
    if(response.ok){
      const data = await response.json();
      await AsyncStorage.setItem('accessToken', data['token']);
      await AsyncStorage.setItem('refreshToken', data['accessToken']);

      const refreshToken = await AsyncStorage.getItem('refreshToken')
      const accessToken = await AsyncStorage.getItem('accessToken')

      console.log(
        'Tokens after refresh are '+ refreshToken+ " "+ accessToken,
    );
    }

    return response.ok;
  }

const isLoggedIn =async()=>{
  const accessToken= await AsyncStorage.getItem('accessToken');
  const response = await fetch('https://localhost:9090/ping',{
    method:'GET',
    headers:{
      Accept:'application/json',
      'Content-Type':'application/json',
      Authorization: 'Bearer '+ accessToken,
      'X-Requested-With':'XMLHttpRequest'

    }
  })
  return response.ok
}


  const handleSubmitButton = async () => {
    try {
      if (!email.trim() || !password.trim()) {
        throw new Error("Email and password are required.");
      }
  
      const response = await fetch("http://localhost:9090/auth/v1/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          email,
          password,
        }),
      });
  
      if (!response.ok) {
        const message = await response.text();
        throw new Error(message);
      }
  
      const data = await response.json();
  
      await AsyncStorage.setItem("accessToken", data['accessToken']);
      await AsyncStorage.setItem("refreshToken", data['refreshToken']);
      setEmail("")
      setPassword("")
      navigation.navigate("Home", {name: "Home"});
      console.log("Signup successful");
    } catch (error) {
      console.error(error);
    }
  };


  return (
    <View style={styles.loginContainer}>
        <CustomBox   style={loginBox}>
            <View style={styles.headerContainer}><CustomText style={textColor.heading} >Login</CustomText></View>

            <TextInput
              placeholder="Your Email"
              value={email}
              onChangeText={(text) => setEmail(text)}
              placeholderTextColor="#888"
              style={styles.textInput}
            />
            

            <TextInput
              placeholder='Password'
              value={password}
              onChangeText={(text) => setPassword(text)}
              placeholderTextColor = '#888'
              style = {styles.textInput}
            />
        </CustomBox>
        <View style={styles.buttonsPanel}>
            <Pressable  style={styles.button} onPressIn={handleSubmitButton}>
              <CustomBox style={buttonBox}>
                <CustomText style={{textAlign : 'center'}}>Submit</CustomText>
              </CustomBox>
            </Pressable>

            <Pressable style={styles.button} onPress={() => navigation.navigate('Signup')}>
              <CustomBox style={buttonBox}>
                <CustomText style={{textAlign : 'center'}}>SignUp</CustomText>
              </CustomBox>
            </Pressable>
        </View>
    </View>
  )
}

const loginBox= {
      mainBox :{
        padding:20,
        borderWidth:1,
        borderRadius:10,
        backgroundColor: '#fff',
        minWidth: 300,
        minHeight: 100,
      },

      shadowBox :{
        backgroundColor:'gray',
        borderRadius:10,
      },
}

const buttonBox= {
  mainBox :{
    padding:10,
    borderWidth:1,
    borderRadius:10,
    backgroundColor: '#fff',

  },

  shadowBox :{
    backgroundColor:'gray',
    borderRadius:10,
    right:-6,
  },
}

const textColor ={
  heading:{
    marginBottom: 20,
    fontSize: 24,
    fontWeight:'bold',
},
}

const styles = StyleSheet.create({
    textInput :{
      backgroundColor: '#f0f0f0',
      borderRadius:5,
      padding:10,
      marginBottom:10,
      width:'100%',
      color:'black'

    },

    loginContainer :{
      flex:1,
      paddingTop:20,
      paddingBottom:20,
      paddingRight:10,
      paddingLeft:10,
      justifyContent: 'center',
      alignItems: 'center',
    },
    button :{
      marginTop:20,
      width:'30%',
    },

    headerContainer:{
      alignItems: 'center',
      marginBottom: 10,
    },

    buttonsPanel:{
      marginTop:10,
      alignItems: 'center',
      minWidth: 300,
      flexDirection: 'row',
      justifyContent: 'space-between',

    }

})
export default Login
