export const queryKeys = {
    auth : {
        all :["auth"] as const ,
        me : ()=> [...queryKeys.auth.all , "me"] as const
    },
    repos:{
        all : ["repos"] as const ,
        list : ()=>[...queryKeys.repos.all , "list"] as const ,
        details : (id:string)=>[...queryKeys.repos.all , "details ,id"] as const ,
        status : (id:string)=>[...queryKeys.repos.all , "status",id] as const ,
    },
    chat:{
        all:["chat"] as const ,
        session:(repositoryId:string)=>[...queryKeys.chat.all , "sessions",repositoryId] as const ,
        messsges:(sessionId:string)=>[queryKeys.chat.all , "messages",,sessionId] as const
    }
}