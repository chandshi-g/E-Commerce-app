import { createRouter, createWebHistory } from 'vue-router'

import Home from '../views/HomeView.vue'
import AddCategory from '../views/Category/AddCategory.vue'
import Category from '../views/Category/Category.vue'
import EditCategory from '../views/Category/EditCategory.vue'
import AddProduct from "../views/Product/AddProduct.vue";
import Product from "../views/Product/Product.vue";
import EditProduct from "../views/Product/EditProduct.vue";
import ShowDetails from "../views/Product/ShowDetails.vue";
import Signup from "../views/Signup.vue";
import Signin from "../views/Signin.vue";
import WishList from "../views/Product/WishList.vue";
import Cart from "../views/Cart/Cart.vue";
import Success from "../views/Payment/Success";
import Failed from "../views/Payment/Failed";
import Checkout from "../views/Checkout/Checkout";
import OrderDetails from "../views/order/OrderDetails.vue";
import OrderHistory from "../views/order/OrderHistory.vue";


const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
  },
  {
    path: '/admin/category/add',
    name: 'AddCategory',
    component: AddCategory,
  },
  {
      path: "/admin/category",
      name: "AdminCategory",
      component: Category,
    },
    {
        path: "/admin/category/:id",
        name: "EditCategory",
        component: EditCategory
      },
      {
          path: "/admin/product/add",
          name: "AddProduct",
          component: AddProduct,
      },
      {
          path: "/admin/product",
          name: "AdminProduct",
          component: Product,
      },
      {
          path: "/admin/product/:id",
          name: "EditProduct",
          component: EditProduct,
      },
      {
          path : '/product/show/:id',
          name : 'ShowDetails',
          component: ShowDetails
      },
      {
          path: '/signup',
          name: 'Signup',
          component: Signup
      },
      {
          path: '/signin',
          name: 'Signin',
          component: Signin
      },
      {
          path: '/wishlist',
          name: 'WishList',
          component: WishList
      },
      {
           path: '/Cart',
           name: 'Cart',
           component: Cart
      },
      {
          path: '/payment/success',
          name: 'PaymentSuccess',
          component:Success
      },
      {
          path: '/payment/failed',
          name: 'FailedPayment',
          component:Failed
      },
      {
          path : '/checkout',
          name : 'Checkout',
          component : Checkout
      },
      {
          path:'/order/:id',
          name:'OrderDetails',
          component: OrderDetails
      },
      {
          path: "/order",
          name: "OrderHistory",
          component: OrderHistory
      },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
})

export default router