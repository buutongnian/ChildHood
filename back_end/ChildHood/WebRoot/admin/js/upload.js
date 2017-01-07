function submit(){
		if($("#versionInput").val()!=""){
			if($("#fileUpload").val()!=""){
				$("#version").val($("#versionInput").val());
				$("#note").val($("#noteInput").val());
				$("#system").val($("#select01").val());
				//alert($("#version").val()+ '/' + $("#version").attr("name") + '/' + $("#note").val() + '/' + $("#system").val() + '/' + $("#uploadPkg").val());
				$("#uploadForm").submit();
				setAlert('文件上传中','文件正在上传中...');
				alert_show();
			}else{
				setAlert('提示','请选择上传文件！');
				alert_show();
			}
		}else{
			setAlert('提示','请输入版本号！');	
			alert_show();
		}
	}
	
	function reset_input(){
		$("#versionInput").val('');
		$("#noteInput").val('');
		$("#select01 option:first").prop("selected",'selected');
		$("#fileUpload").val('');
		$(".filename").val('没有选中文件...');
	}
	
	function setAlert(head,val){
		$("#success-alert h4").html(head);
		$("#success-alert span").html(val);
	}
	
	function alert_show(){
		$("#success-alert").show();
	}
	
	function alert_hide(){
		$("#success-alert").hide();
	}