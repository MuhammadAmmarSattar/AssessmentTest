package com.muhammad.presentation.productScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.core.network.model.Product
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.muhammad.presentation.components.RatingBars
import com.muhammad.presentation.viewmodel.ProductViewModel


@Composable
fun HomeProductScreen(
    modifier: Modifier = Modifier,
    selectProduct: (Product) -> Unit,
    productViewModel: ProductViewModel = hiltViewModel(),
){
    val res = productViewModel.productStateFlow.value

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.isStatusBarVisible = true
        systemUiController.setStatusBarColor(color = Color.White)
        systemUiController.setNavigationBarColor(
            color = Color.White,
        )
    }
  if (res.loading) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
      CircularProgressIndicator()
    }
  }
  if (res.error.isNotBlank()) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
      Text(text = res.error)
    }
  }
  res.product?.let { productList ->
          LazyColumn(    modifier = Modifier
              .fillMaxSize()
              .padding(bottom = 50.dp)) {
              items(productList) { it ->
                  ProductItems(product = it,selectProduct =selectProduct)
              }
              item {
                  Spacer(modifier = Modifier.padding(bottom = 50.dp))
              }
          }
  }
}

@Composable
fun ProductItems(product: Product,   selectProduct: (Product) -> Unit = {},) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = { selectProduct(product) }
            )
            .height(216.dp)
            .padding(15.dp), shape = RoundedCornerShape(8.dp),
        elevation = 5.dp
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(product.image)
                .crossfade(true)
                .build(),
            contentDescription = "ImageRequest example",
            placeholder = painterResource(com.muhammad.core_ui.R.drawable.ic_logo),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .size(180.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            Text(
                text = product.price.toString()+" AED",
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(10.dp),
                style = TextStyle(color = Color.White, fontSize = 19.sp , fontWeight = FontWeight.Bold)
            )
            RatingBars(rating = 3.0)
        }

    }
    Text(
        text = product.title,
        modifier = Modifier
            .wrapContentWidth()
            .padding(top = 10.dp, start = 12.dp),
        style = TextStyle(color = Color.Black, fontSize = 12.sp , fontWeight = FontWeight.Light, fontStyle = FontStyle.Italic)
    )
    Text(
        text = product.description?:"",
        modifier = Modifier
            .wrapContentWidth()
            .padding(top = 1.dp, start = 12.dp, end = 12.dp, bottom = 20.dp),
        style = TextStyle(color = colorResource(id = com.muhammad.core_ui.R.color.light_black_700), fontSize = 13.sp )
    )
}