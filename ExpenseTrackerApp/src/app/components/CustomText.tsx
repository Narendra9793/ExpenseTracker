import { StyleSheet, Text } from 'react-native'
import React, { ReactNode } from 'react';


interface CustomBoxProps {
  style?:any
  children?: ReactNode;
}

const CustomText = ({style={}, children, ...props} : CustomBoxProps) => {
  return (
    <Text style={[styles.heading, style]} {...props}>
        {children}
    </Text>
  )
}

const styles = StyleSheet.create({
  heading:{

  }
})

export default CustomText

