<#import "template.ftl" as layout>
<@layout.registrationLayout displayInfo=social.displayInfo; section>
    <#if section = "title">
        ${msg("loginTitle",(realm.displayName!''))}
    <#elseif section = "header">
        <link href="https://fonts.googleapis.com/css?family=Montserrat:100,100i,200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet"/>
        <link href="${url.resourcesPath}/img/rostov-gosud-univer_1.png)" rel="icon"/>
        <script>
            function togglePassword() {
                var x = document.getElementById("password");
                var v = document.getElementById("vi");
                if (x.type === "password") {
                    x.type = "text";
                    v.src = "${url.resourcesPath}/img/eye.png";
                } else {
                    x.type = "password";
                    v.src = "${url.resourcesPath}/img/eye-off.png";
                }
            }
        </script>
    <#elseif section = "form">
        <div>
            <img class="logo" src="${url.resourcesPath}/img/rostov-gosud-univer_1.png" alt="" height="130px">
        </div>
        <div class="box-container">
            <div>
                <p class="application-name">Информационная система учета лицензий на ПО "РГЭУ (РИНХ)"</p>
            </div>
            <#if realm.password>
                <div>
                    <form id="kc-form-login" class="form" onsubmit="return true;" action="${url.loginAction}" method="post">
                        <input id="username" class="login-field" placeholder="Логин" type="text" name="username" tabindex="1">
                        <div>
                            <label class="visibility" id="v" onclick="togglePassword()">
                                <img id="vi" src="${url.resourcesPath}/img/eye-off.png">
                            </label>
                        </div>
                        <input id="password" class="login-field" placeholder="Пароль" type="password" name="password" tabindex="2">
                        <input class="submit" type="submit" value="Войти" tabindex="3">
                    </form>
                </div>
            </#if>
            <#if social.providers??>
                <p class="para">${msg("selectAlternative")}</p>
                <div id="social-providers">
                    <#list social.providers as p>
                        <input class="social-link-style" type="button" onclick="location.href='${p.loginUrl}';" value="${p.displayName}"/>
                    </#list>
                </div>
            </#if>
        </div>
        <div>
            <p class="copyright" style="color: rgb(255, 255, 255); font-weight: 700; font-family: Montserrat; font-size: 16px;">Ростов-на-Дону, 2024г.</p>
        </div>
    </#if>
</@layout.registrationLayout>
