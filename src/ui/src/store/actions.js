import * as Types from './types'
import { get } from '../utils/APIUtil'
import { URLs } from '../context/Consts'

export default {
    [Types.SET_BLOG_INFO]: ({ commit }) => {
        get(URLs.blog.base, null, response => commit(Types.SET_BLOG_INFO, response.data))
    }
}