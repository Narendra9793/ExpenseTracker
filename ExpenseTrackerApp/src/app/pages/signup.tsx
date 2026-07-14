import { Pressable, StyleSheet, TextInput, View } from 'react-native'
import React, {  useState } from 'react'
import CustomBox from '../components/CustomBox'
import CustomText from '../components/CustomText'
import AsyncStorage from '@react-native-async-storage/async-storage';

const SignUp = ({navigation}) => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');


  const handleSubmitButton = async () => {
    try {
      if (!email.trim() || !password.trim()) {
        throw new Error("Email and password are required.");
      }
  
      const response = await fetch("http://localhost:9090/auth/v1/signup", {
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
  
      await AsyncStorage.setItem("accessToken", data.accessToken);
      await AsyncStorage.setItem("refreshToken", data.refreshToken);
      setEmail("")
      setPassword("")
      navigation.replace("Home")
      console.log("Signup successful");
    } catch (error) {
      console.error(error);
    }
  };

  
  return (
    <View style={styles.loginContainer}>
        <CustomBox   style={loginBox}>
        <View style={styles.headerContainer}><CustomText style={textColor.heading} >SignUp</CustomText></View>
            <TextInput
              placeholder='Your Email'
              value={email}
              onChangeText={(text) => setEmail(text)}
              placeholderTextColor = '#888'
              style = {styles.textInput}
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

            <Pressable style={styles.button} onPress={() => navigation.navigate('Login')}>
              <CustomBox style={buttonBox}>
                <CustomText style={{textAlign : 'center'}}>Login</CustomText>
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
  heading:{
      marginBottom: 20,
      fontSize: 20,
      fontWeight:'bold',
  },
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
    padding:20,
    justifyContent: 'center',
    alignItems: 'center',
  },    button :{
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
export default SignUp