import { StyleSheet , View } from 'react-native'
import React, { ReactNode } from 'react';
import { Box } from '@/components/ui/box';

interface CustomBoxProps {
  style?: {
    mainBox?: any;
    shadowBox?: any;
  };
  children?: ReactNode;
}

const CustomBox = ({
  style = {},
  children,
  ...props
}: CustomBoxProps) => {
  return (
    <View >
      <Box {...props} style={[styles.mainBox, style.mainBox]}>
        {children}
      </Box>

      <Box
        {...props}
        style={[styles.shadowBox, style.shadowBox]}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  mainBox :{
    padding:20,
    borderColor:'black',
    borderWidth:1,
    position:'relative',
    backgroundColor: 'black',
    
  },
  shadowBox :{
    position:"absolute",
    borderWidth:1,
    left:8,
    right:-8,
    bottom:-8,
    top:8,
    backgroundColor:'gray',
    zIndex:-1,
  },

})
export default CustomBox


