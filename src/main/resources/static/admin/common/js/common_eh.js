// script 공통 로직은 이 파일에 작업 부탁드립니다. 

// ------------- 에디터 함수 S -----------------
//에디터 설정
function fnSummernoteCall(){
	
	$('#summernote').summernote({
				height: 300,                 // 에디터 높이
				minHeight: null,             // 최소 높이
				maxHeight: null,             // 최대 높이
				focus: true,                 // 에디터 로딩후 포커스를 맞출지 여부
				lang: "ko-KR",				 // 한글 설정
				placeholder: '최대 2048자까지 쓸 수 있습니다',	//placeholder 설정
				
				callbacks: {	//여기 부분이 이미지를 첨부하는 부분
					onImageUpload : function(files) {
					
						uploadSummernoteImageFile(files[0],this);
					 },
					onPaste: function (e) {
						var clipboardData = e.originalEvent.clipboardData;
						if (clipboardData && clipboardData.items && clipboardData.items.length) {
							var item = clipboardData.items[0];
							if (item.kind === 'file' && item.type.indexOf('image/') !== -1) {
								e.preventDefault();
							}
						}
					}
				}
	});
}

// 이미지 파일 업로드
function uploadSummernoteImageFile(file, editor) {

	data = new FormData();
	data.append("file", file);
	var header = $("meta[name='_csrf_header']").attr("content");
    var token = $("meta[name='_csrf']").attr("content");
   
	$.ajax({
		data : data,
		type : "POST",
		url : "/uploadSummernoteImageFile",
		beforeSend: function(xhr){
			
		 if(header != null &&  token != null){
			xhr.setRequestHeader(header, token);
		 }
  
        },
		contentType : false,
		processData : false,
		success : function(data) {
        	//항상 업로드된 파일의 url이 있어야 한다.
			$(editor).summernote('insertImage', data.url);
		}
	});
}


// 첨부파일 드래그
$("div.note-editable").on('drop',function(e){
         for(i=0; i< e.originalEvent.dataTransfer.files.length; i++){
         	uploadSummernoteImageFile(e.originalEvent.dataTransfer.files[i],$("#summernote")[0]);
         }
        e.preventDefault();
   })
   
// ------------- 에디터 함수 S -----------------   
   

// ------------- 멀티파일 추가 S -----------------   
   //파일 추가시
$("#file_add").on('click',function(){ $('#multi-add').click(); });


var $fileListArr = new Array();
 var $totSize = 0;
 var $keyNum = 0;
 var $limit = 0;
 

$("#multi-add").on('change',function(){
	
   $("#fileNoData").remove();
 
	var files = $(this)[0].files;

	var fileArr = new Array();

	fileArr = $fileListArr;
	$limit = $totSize;
	for(var i = 0 ; i < files.length ; i++){
		$limit = $limit + files[i].size;
		if($limit > 20000000){
			alert("첨부파일 용량은 20MB를 넘길수 없습니다.");
			return false;
		}
	}

	for(var i = 0 ; i < files.length ; i++){
		 $('#file_table').append("<tr id=file"+ $keyNum +"><td class='txt-c'><button type='button' style='color: #A4A4A4; font-size: large;' class='deleteFile' >&#8861;</button></td>"+
					"<td>"+ files[i].name +"</td>"+
					"<td id='fileSize "+ $keyNum +"'><p class=file"+ $keyNum +">"+ Math.floor(files[i].size / 1000) +" KB</p></td>"+
					"</tr>");
		 $keyNum++;
		 fileArr.push(files[i]);
		 $totSize = $totSize + files[i].size;
	}


	$fileListArr = new Array();
	$fileListArr = fileArr;
	//$('#totSize').text("");
	//$('#totSize').text(Math.floor($totSize / 1000000));
	
	
		var fileList = new FormData();
		for(var i = 0; i < $fileListArr.length ; i++){
			fileList.append("uploadFile" , $fileListArr[i]);
			
		}
		//$("#multiFile").val(fileList);
		
});


//파일 화면 delete
$(document).on("click" , '.deleteFile', function(){
	//삭제할 파일의 아이디
	var DeleteID = $(this).parent().parent().attr('id');

	//삭제하려는 파일의 크기값(텍스트)
	var DeleteFileSize = $(this).parent().next().next().children('p').text();

	//삭제하는 파일의 크기값(바이트)
	var fileSizeByteArr = new Array();
	fileSizeByteArr = DeleteFileSize.split(' ');
	var fileSize = Number(fileSizeByteArr[0]) * 1000;

	//배열에서 삭제를 위한 번호
	var DeleteArrNum = DeleteID.substring(DeleteID.length , DeleteID.length -1);

	var fileArr = $fileListArr;

	fileArr.splice(DeleteArrNum , 1);
	$keyNum = 0
	$fileListArr = new Array();
	$('#file_table').children().remove();
	 $totSize = 0;
	for(var i = 0 ; i < fileArr.length ; i++){

		 $('#file_table').append("<tr id=file"+ $keyNum +"><td class='txt-c'><button type='button' style='color:  #A4A4A4; font-size: large;' class='deleteFile' >&#8861;</button></td>"+
					"<td>"+ fileArr[i].name +"</td>"+
					"<td id='fileSize "+ $keyNum +"'><p class=file"+ $keyNum +">"+ Math.floor(fileArr[i].size / 1000) +" KB</p></td>"+
					"</tr>");
		 $keyNum++;
		 $fileListArr.push(fileArr[i]);
		 $totSize = $totSize + fileArr[i].size;
	}

	$limit = $totSize;
	$('#totSize').text("");
	$('#totSize').text(Math.floor($totSize / 1000000));

});


//파일 db,물리 삭제 
function fnfileDelete( atchFileSn, fileSeq ){

   $.ajax( {
      url: "/file/fileDelete",
      type: "GET",
      dataType: "json",
      data: { atchFileSn:  atchFileSn , fileSeq : fileSeq },
      contentType: "application/json",
      success: function ( result ) {
	       
	      alert("파일이 정상적으로 삭제 되었습니다.")
	      $("#attachFileList").remove(); // 파일 목록 삭제
	      var html  = "";   
			  html += "<div id='attachFileList'><div lass='upload-thumb-wrap'>";   
	      
	        if( result.length > 0 ) { 
	              html  +=" </div>"
	           for ( i = 0 ; i < result.length ; i++ ) {
		           html +='<div>';  
		           html += '<span>'  + result[i].orignlFileNm +'</span>'; 
		           html += '<span  class="badge badge-dark"  style="cursor:pointer"> <a href="/fileDownloand?atchFileSn=\'' +  result[i].atchFileDetailPk.atchFileSn + '\'&fileSeq=\''+  result[i].atchFileDetailPk.fileSeq    + '\');">다운로드</a></span>'; 
		           html += '<span  class="badge badge-dark"  style="cursor:pointer"> <a onclick="javascript:fnfileDelete(\'' +  result[i].atchFileDetailPk.atchFileSn + '\',\'' +  result[i].atchFileDetailPk.fileSeq + '\');" >삭제</a></span>';
		           html += "</div>";  
	           }	 
	  
	        } else {
				   html += "<p class='card-text'>첨부파일 목록이 없습니다.</p>";  
			}
	      
	      html  +="</div></div>"
	
		 $("#attachFileDiv").html(html); // 답글목록 셋팅
      }
    });
}
// ------------- 멀티파일 추가 E -----------------   
