package com.example.snivvy.present.registrationscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.snivvy.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntSize // <-- NEW IMPORT
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.navigation.NavHostController
import com.example.snivvy.present.navigation.Routes

@Composable
@Preview(showSystemUi = true)
fun UserRegistrationScreen(){
    //dropdown
    var expanded by remember{
        mutableStateOf(false)
    }
    var selectedCountry by remember{
        mutableStateOf("India")
    }
    var countryCode by remember{
        mutableStateOf("+91")
    }
    var phoneNumber by remember{
        mutableStateOf("")
    }
    var anchorSize by remember {
        mutableStateOf(IntSize.Zero) }
    val density = LocalDensity.current
    Box(modifier=Modifier.fillMaxSize().background(Color(0xFFF5F0E6)).padding(bottom=100.dp)) {


        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "What's Your Phone Number",
                fontSize = 23.sp,
                color = colorResource(R.color.brown_snivvy),
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier.padding(bottom=80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(R.drawable.phonimage), contentDescription = null)
        }
        TextButton(onClick = {expanded=true},modifier=Modifier.fillMaxWidth()) {
            Box(modifier=Modifier.width(230.dp)){
                Text(text=selectedCountry,
                    modifier=Modifier.align(Alignment.Center).padding(top=100.dp),
                    fontSize=18.sp,
                    fontWeight=FontWeight.Bold,
                    color=colorResource(R.color.black)
                )
                Icon(imageVector=Icons.Default.ArrowDropDown,
                    contentDescription=null,
                    modifier=Modifier.align(Alignment.Center).padding(top=100.dp,start=64.dp),
                    tint=colorResource((R.color.black)))
            }
        }
        HorizontalDivider(modifier=Modifier.padding(horizontal=150.dp).padding(top=130.dp),
            thickness=3.dp,
            color=colorResource(R.color.black))

        DropdownMenu(expanded=expanded, onDismissRequest = {expanded=false},modifier=Modifier.width(230.dp),
            offset = with(density) {
                // anchorSize.width is Int. We use .toDp() to convert it inside this scope.
                val buttonWidthDp = anchorSize.width.toDp()
                val dropdownWidthDp = 230.dp

                // Calculate horizontal shift: (Anchor Center) - (Dropdown Center)
                val offsetX = (buttonWidthDp / 2f) - (dropdownWidthDp / 2f)

                DpOffset(x = offsetX, y = 0.dp) // Returns the required DpOffset
            }
        ) {
            listOf("India","USA","Japan","Australia","China","Canada").sorted().forEach{
                country->
                DropdownMenuItem(text={Text(text=country)},onClick={
                    selectedCountry=country
                    expanded=false
                })

            }
        }

        Column(modifier=Modifier.fillMaxWidth().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally){
           Row{
               TextField(value=countryCode,onValueChange={
                   countryCode=it
               },modifier=Modifier.width(70.dp).padding(top=220.dp),
                   singleLine=true,
                   textStyle= LocalTextStyle.current.copy(fontSize=18.sp),
                   colors= TextFieldDefaults.colors(
                       unfocusedContainerColor = Color.Transparent,
                       focusedContainerColor = Color.Transparent,
                       unfocusedIndicatorColor = colorResource(R.color.brown_snivvy),
                       focusedIndicatorColor = colorResource(R.color.brown_snivvy)
                   )
                   )
               Spacer(modifier=Modifier.width(10.dp))

               TextField(value=phoneNumber,onValueChange={
                   phoneNumber = it
               },modifier=Modifier.padding(top=220.dp),
                   placeholder={Text(text="Phone Number")},
                   singleLine = true,
                   colors= TextFieldDefaults.colors(
                       unfocusedContainerColor = Color.Transparent,
                       focusedContainerColor = Color.Transparent,
                       unfocusedIndicatorColor = colorResource(R.color.brown_snivvy),
                       focusedIndicatorColor = colorResource(R.color.brown_snivvy)
                   )
               )

           }
            Spacer(modifier=Modifier.height(8.dp))
            Button(onClick={},modifier=Modifier.width(100.dp).height(45.dp),shape= RoundedCornerShape(7.dp)
                ,colors= ButtonDefaults.buttonColors(containerColor = colorResource(R.color.brown_snivvy2))) {
                Text(text="Next",color=colorResource(R.color.background_snivvy),
                    fontSize = 18.sp,
                    fontWeight=FontWeight.Bold,
                    )
            }
        }


    }
}

