<#import "/spring.ftl" as spring />
<!doctype html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
  <head>
    <base href="<@spring.url basePath/>">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Eureka</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width">

    <link rel="stylesheet" href="eureka/css/wro.css">

  </head>

  <body id="one">
    <#include "header.ftlh">
    <div class="container-fluid xd-container">
      <#include "navbar.ftlh">
      <h1>Instances currently registered with Eureka</h1>
      <table id='instances' class="table table-striped table-hover">
        <thead>
          <tr><th>Application</th><th>AMIs</th><th>Availability Zones</th><th>Status</th></tr>
        </thead>
        <tbody>
          <#if apps?has_content>
            <#list apps as app>
              <tr>
                <td><b>${app.name}</b></td>
                <td>
                  <#list app.amiCounts as amiCount>
                    <b>${amiCount.key}</b> (${amiCount.value})<#if amiCount_has_next>,</#if>
                  </#list>
                </td>
                <td>
                  <#list app.zoneCounts as zoneCount>
                    <b>${zoneCount.key}</b> (${zoneCount.value})<#if zoneCount_has_next>,</#if>
                  </#list>
                </td>
                <td>
                  <#list app.instanceInfos as instanceInfo>
                    <#if instanceInfo.isNotUp>
                      <font color=red size=+1><b>
                    </#if>
                    <b>${instanceInfo.status}</b> (${instanceInfo.instances?size}) -
                    <#if instanceInfo.isNotUp>
                      </b></font>
                    </#if>
                    <#list instanceInfo.instances as instance>
                      <#if instance.isHref>
                        <a href="${instance.url}" target="_blank">${instance.id}</a>
                        <#-- 修改eureka页面，支持上线和下线应用-->
                        <#if instanceInfo.status=='OUT_OF_SERVICE'||instanceInfo.status=='DOWN'>
                          <b><button href="#" style="color: #5fa134;" onclick="sendUP('${app.name}','${instance.id}')">UP</button></b>
                        <#else>
                          <b><button href="#"  style="color: #FF000E;" onclick="sendOUTOFSERVICE('${app.name}','${instance.id}')">DOWN</button></b>
                        </#if>
                        <br>
                        <#-- 修改eureka页面，支持上线和下线应用-->
                      <#else>
                        ${instance.id}
                      </#if><#if instance_has_next>,</#if>
                    </#list>
                  </#list>
                </td>
              </tr>
            </#list>
          <#else>
            <tr><td colspan="4">No instances available</td></tr>
          </#if>

        </tbody>
      </table>

      <h1>General Info</h1>

      <table id='generalInfo' class="table table-striped table-hover">
        <thead>
          <tr><th>Name</th><th>Value</th></tr>
        </thead>
        <tbody>
          <#list statusInfo.generalStats?keys as stat>
            <tr>
              <td>${stat}</td><td>${statusInfo.generalStats[stat]!""}</td>
            </tr>
          </#list>
          <#list statusInfo.applicationStats?keys as stat>
            <tr>
              <td>${stat}</td><td>${statusInfo.applicationStats[stat]!""}</td>
            </tr>
          </#list>
        </tbody>
      </table>

      <h1>Instance Info</h1>

      <table id='instanceInfo' class="table table-striped table-hover">
        <thead>
          <tr><th>Name</th><th>Value</th></tr>
        <thead>
        <tbody>
          <#list instanceInfo?keys as key>
            <tr>
              <td>${key}</td><td>${instanceInfo[key]!""}</td>
            </tr>
          </#list>
        </tbody>
      </table>
    </div>
    <script type="text/javascript" src="eureka/js/wro.js" ></script>
    <script type="text/javascript">
      $(document).ready(function() {
        $('table.stripeable tr:odd').addClass('odd');
        $('table.stripeable tr:even').addClass('even');
      });

      // 修改eureka页面，支持上线和下线应用
      function sendUP(appName,instanceId){
        var url = window.location.origin+"/eureka/apps/"+appName+"/"+instanceId+"/status?value=UP"
        console.info(url)
        sendPut(url)
      }
      function sendDOWN(){

      }
      function sendOUTOFSERVICE(appName,instanceId){
        var url = window.location.origin+"/eureka/apps/"+appName+"/"+instanceId+"/status?value=OUT_OF_SERVICE"
        console.info(url)
        sendPut(url)
      }
      function sendPut(url) {
        $.ajax({
          url:url,
          type:"PUT",
          async:true,
          success:function (data) {
            console.info(data)
            alert("操作成功")
            window.location.reload()
          },
          error:function (data) {
            console.error(data)
            alert("操作失败")
            window.location.reload()
          }
        });
      }
      // 修改eureka页面，支持上线和下线应用
    </script>
  </body>
</html>
