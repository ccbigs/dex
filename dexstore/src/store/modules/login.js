const login={



    /*公共state对象，存储所有组件状态*/
    state:{
        login: {
            state: false
        }
    },

    /*唯一取值的方法，计算属性*/
    getters:{
        getLogin(state){
            return state.login;
        }
    },

    //唯一可以修改state值的方法，同步阻塞
    mutations:{
        updateLogin(state,Login){
            state.login = Login;
        }
    },

    //异步调用mutations方法
    actions : {
        asyncUpdateUser(context, Login) {
            /*commit调用的就是mutations中的东西*/
            context.commit('updateLogin', Login);
        }
    }
}


export default login;
