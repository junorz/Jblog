import Vue from 'vue'
import Router from 'vue-router'
import Home from './components/Home'
import Initialize from './components/Initialize'
import Login from './components/Login'
import Admin from './components/Admin'
import Post from './components/Post'
import { get } from './utils/APIUtil'
import { URLs } from './context/Consts'

Vue.use(Router)

const router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
    },
    {
      path: '/initialize',
      name: 'initialize',
      component: Initialize
    },
    {
      path: '/login',
      name: 'login',
      component: Login
    },
    {
      path: '/admin',
      name: 'admin',
      component: Admin,
      meta: { authRequired: true }
    },
    {
      path: '/posts/:id',
      name: 'posts',
      component: Post
    }
    //{
    //path: '/about',
    //name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    //component: () => import(/* webpackChunkName: "about" */ './views/About.vue')
    //}
  ]
});

router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.authRequired)) {
    // check if user has logged in already.
    get(URLs.blog.auth, null, response => {
      if (response.data == true) {
        next();
      } else {
        next("/login")
      }
    }, () => next("/login"))
  } else {
    next();
  }
})


export default router;