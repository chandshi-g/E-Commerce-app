import { createRouter, createWebHistory } from 'vue-router'

import Home from '../views/HomeView.vue'
import AddCategory from '../views/Category/AddCategory.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
  },
  {
    path: '/about',
    name: 'About',
    component: () => import('../views/AboutView.vue'),
  },
  {
    path: '/admin/category/add',
    name: 'AddCategory',
    component: AddCategory,
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
})

export default router