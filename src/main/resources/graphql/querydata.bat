
//返回单个数据
{
     bookById(id:"book-1"){
         name
         author{
             firstName
         }
     }
}
//返回数组
{

    books{
        name
        author{
            firstName
        }
    }
}

//接口聚合
{
     bookById(id:"book-1"){
         name
         author{
             firstName
         }
     }

    books{
        name
        author{
            firstName
        }
    }

}