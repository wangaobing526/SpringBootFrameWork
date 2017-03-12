<html>  
    <head>  
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
    <title>文件上传</title>  
    </head>  
    <body>  
        <form action="fileUpload" method="post" enctype="multipart/form-data">  
        <table>  
        <tr>  
        <td>
            创建人：
            <input id="createUser" type="text" name="createUser"  value=""/>
            <td>
            </tr>
              <tr>  
        <td>
            原始文件名称：
            <input id="originalFileName" type="text" name="originalFileName"  value=""/>
             <td>
            </tr>
             <tr>  
        <td>
            备注：
            <input id="remark" type="text" name="remark"  value=""/>
             <td>
            </tr>
              <tr>  
        <td>
           事项ID:
            <input id="instanceId" type="text" name="instanceId"  value=""/>
            <td>
            </tr>
             <tr>  
        <td>
          任务ID：
            <input id="taskId" type="text" name="taskId"  value=""/>
              <td>
            </tr>
             <tr>  
        <td>
          文件类型：
            <input id="fileType" type="text" name="fileType"  value="s0"/>
             <td>
            </tr>
               <tr>  
        <td>
            <input  type="file" name="fileUpload" />  
               <td>
            </tr>
              <tr>  
        <td>
            <input type="submit" value="上传" />  
             <td>
            </tr>
         </table>
        </form>  
    </body>  
</html>  