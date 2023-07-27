package com.muhammad.presentation.details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.core.network.model.Product
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.muhammad.core_ui.R
import com.muhammad.presentation.components.DropShadowTopToBottom
import com.muhammad.presentation.components.RatingBars
import com.skydoves.landscapist.coil.CoilImage
import kotlinx.coroutines.launch

@Composable
fun ProductDetail(productDetail: Product, pressOnBack: () -> Unit = {}) {

    PosterDetailsBody(product= productDetail,pressOnBack)


}

@Composable
private fun PosterDetailsBody(
    product: Product,
    pressOnBack: () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.isStatusBarVisible = true
        systemUiController.setStatusBarColor(Color(0x44000000))
    }
    Surface() {
        BottomSheet(product = product,pressOnBack =pressOnBack )
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheet(product:Product,pressOnBack: () -> Unit) {
    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )


    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetShape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
            ) {

                val scope = rememberCoroutineScope()
                var isArrowUp = remember { mutableStateOf(true) }
                Column(modifier = Modifier.padding(16.dp)) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(painter = painterResource(id = com.muhammad.core_ui.R.drawable.arrow_icon),
                            contentDescription = "asdas",
                            modifier = Modifier
                                .size(14.dp)
                                .clickable {
                                    scope.launch {
                                        if (sheetState.isCollapsed) {
                                            sheetState.expand()
                                        } else {
                                            sheetState.collapse()
                                        }
                                    }
                                })
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RoundedCardWithImage(imagePainter =painterResource( com.muhammad.core_ui.R.drawable.share))

                        Spacer(modifier = Modifier.width(16.dp))

                        SaveButton(modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                            .padding(2.dp), text = "Order now", onClick = {
                        })
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = product.title,
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(top = 10.dp, start = 12.dp),
                        style = TextStyle(color = Color.Black, fontSize = 12.sp , fontWeight = FontWeight.Light, fontStyle = FontStyle.Italic)
                    )
                    Text(
                        text = product.description?:"asd",
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(top = 1.dp, start = 12.dp, end = 12.dp, bottom = 20.dp),
                        style = TextStyle(color = colorResource(id = com.muhammad.core_ui.R.color.light_black_700), fontSize = 13.sp )
                    )

                    ReviewCard(product= product)
                }

            }
        },
        sheetPeekHeight = 215.dp,
        sheetBackgroundColor = Color.White
    ) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(product.image)
                .crossfade(true)
                .build(),
            contentDescription = "ImageRequest example",
            placeholder = painterResource(R.drawable.ic_logo),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize(),
        )


    }
        Column( modifier = Modifier
            .fillMaxWidth()
            .background(Color(0x44000000))
            .padding(15.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = com.muhammad.core_ui.R.drawable.more_icon),
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(58.dp)
                        .clickable(onClick = { pressOnBack() })
                )

                Image(
                    painter = painterResource(id = com.muhammad.core_ui.R.drawable.back_icon),
                    contentDescription = "More Info",
                    modifier = Modifier.size(58.dp)
                )
            }
            Text(
                text = "Details",
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(top = 6.dp),
                style = TextStyle(color = Color.White, fontSize = 26.sp , fontWeight = FontWeight.Bold))
        }



}

@Composable
fun SaveButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Button(
        onClick = { onClick() },
        shape = CircleShape,
        enabled = enabled,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(id = com.muhammad.core_ui.R.color.light_blue), // Set transparent background for ripple effect
            contentColor = colorResource(id = com.muhammad.core_ui.R.color.white) // Set your custom text color
        )
    ) {
        Text(text = text, modifier = Modifier.padding(top = 6.dp, bottom = 6.dp))
    }
}

@Composable
fun ReviewCard(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(105.dp)
            .padding(16.dp),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = colorResource(id = com.muhammad.core_ui.R.color.gray_600)
    ) {
        Column(modifier = Modifier.padding(5.dp)) {
            Text(text = "Reviews (${product.rating.count})", style = MaterialTheme.typography.body1)
            Row(  modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically
                ) {
                Text(text = "5.00", fontSize = 23.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(start = 5.dp))
                Spacer(modifier = Modifier.width(25.dp))
                RatingBars(rating = 5.0)
            }
        }
    }
}


@Composable
fun RoundedCardWithImage(imagePainter: Painter) {
    Card(
        modifier = Modifier
            .width(40.dp)
            .height(40.dp),
        shape = RoundedCornerShape(12.dp),
        backgroundColor = Color.White, // You can set any color you like for the card
        elevation = 4.dp // Adjust the elevation as needed
    ) {
        Image(
            painter = imagePainter,
            contentDescription = "Image",
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp) // Adjust the padding as needed
        )
    }
}