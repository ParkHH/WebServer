//-----------------------------------------------------------------
// Paging 처리
//-----------------------------------------------------------------
function pagination(count, currentPage){
	totalRecode = count;
	currentPage = currentPage;
	pageSize = 10;
	totalPage = Math.ceil(totalRecode/pageSize);
	blockSize = 5;
	firstPage = currentPage-(currentPage-1)%blockSize;
	lastPage = firstPage+(blockSize-1);
	curPos = (currentPage-1)*pageSize;
	num = totalRecode-curPos;
	var next = lastPage+1;
    var prev = firstPage-1;
    
	 var $pingingView = $("#pageNumber");
        var html = "";
        if(prev > 0){
        	html += "<a href=# id='prev'>◀</a> ";
        }
        for(var i=firstPage; i <= lastPage; i++){
        	if(i>totalPage){
        		break;
        	}
            html += "<a href='#' id=" + i + ">" + i + "</a> ";
        }
        if(lastPage < totalPage){
        	html += "<a href=# id='next'>▶</a>";
        }
        $("#pageNumber").html(html);    // 페이지 목록 생성
        $("#pageNumber a").css("color", "black");
        $("#pageNumber a#" + currentPage).css({"text-decoration":"none", "color":"red", "font-weight":"bold"});    // 현재 페이지 표시 효과
        $("#pageNumber a").click(function(){	     	// Page 변경을 위해 Pagination 을 클릭했다면
            var $item = $(this);						// 눌린 DOM 객체를 ITEM 변수에 담는다.
            var $id = $item.attr("id");					// ITEM 변수에 담긴 DOM 객체에 지정된 ID 속성을 가져와 ID 변수에 담는다.
            var selectedPage = $item.text();			// ITEM 변수에 담긴 DOM 객체에 담긴 TEXT 를 가져와 변수에 담는다. (Pagination 에 지정된 Text 는 Page 번호이다)
            
            if($id == "next"){							// click 한 Pagenation 의 id 값이 next 이면 (즉 누른 Pagination 이 다음 PageBlock 으로 넘어가는것이였을때)
            	selectedPage = next;					// 선택한 Page 값을 현재 마지막 Page 번호에 1을 더한 값으로 지정
            }
            if($id == "prev"){							// 이전 PageBlock 으로 넘어가는 Pagination 을 Click 했을때
            	selectedPage = prev;					// 선택한 Page 값을 현재의 첫번쨰 Page 번호에 1을 뺸값으로 지정
            }
            pagination(totalRecode, selectedPage);		// 선택한 Page 번호가 Pagination 의 Click Event 발생으로 변경되었으므로 Pagination 변수들을 초기화하여 선택한 Page 번호를 기준으로 맞게끔 재구성한다.
            $("table > tbody").empty();					// Table 을 한번 비우고
			for(var i=0; i<pageSize; i++){				// Pagesize 만큼 반복하여 Data 를 출력
				if(num<1){								
					break;
				}
				var file = fileList[curPos++];
				$($("table").find("tbody")).append("<tr><td>"+(num--)+"</td><td><a href=\"/template/download?file_name="+file.file_name+"&seq="+file.seq+"\">"+file.file_name+"</a></td><td>"+file.uploader+"</td><td>"
														+file.date+"</td><td>"+file.hit+"</td><td style=\"display:none;\">"+file.seq+"</td><td style=\"width:10%;\"><input type=\"button\" name=\"bt_delete\" value=\"삭제하기\" onClick=\"runBtDelete(this)\"></td></tr>");
			}	
        });                                    
   }







//-----------------------------------------------------------------
//Paging 처리 - Overloading
//-----------------------------------------------------------------
function pagination2(count, currentPage, id, tableId, dataList){
	totalRecode = count;
	currentPage = currentPage;
	pageSize = 10;
	totalPage = Math.ceil(totalRecode/pageSize);
	blockSize = 5;
	firstPage = currentPage-(currentPage-1)%blockSize;
	lastPage = firstPage+(blockSize-1);
	curPos = (currentPage-1)*pageSize;
	num = totalRecode-curPos;
	var next = lastPage+1;
	var prev = firstPage-1;			
	
	//alert(count+","+currentPage+","+id+","+tableId+","+curPos);
	
	 var $pingingView = $(id);
     var html = "";
     if(prev > 0){
     	html += "<a href=# id='prev'>◀</a> ";
     }
     for(var i=firstPage; i <= lastPage; i++){
     	if(i>totalPage){
     		break;
     	}
         html += "<a href='#' id=" + i + ">" + i + "</a> ";
     }
     if(lastPage < totalPage){
     	html += "<a href=# id='next'>▶</a>";
     }
     $(id).html(html);    // 페이지 목록 생성
     $(id+"a").css("color", "black");
     $(id+" a#" + currentPage).css({"text-decoration":"none", "color":"red", "font-weight":"bold"});    // 현재 페이지 표시 효과
     $(id+" a").click(function(){	     									// Page 변경을 위해 Pagination 을 클릭했다면
         var $item = $(this);												// 눌린 DOM 객체를 ITEM 변수에 담는다.
         var $id = $item.attr("id");										// ITEM 변수에 담긴 DOM 객체에 지정된 ID 속성을 가져와 ID 변수에 담는다.
         var selectedPage = $item.text();									// ITEM 변수에 담긴 DOM 객체에 담긴 TEXT 를 가져와 변수에 담는다. (Pagination 에 지정된 Text 는 Page 번호이다)
         
         if($id == "next"){													// click 한 Pagenation 의 id 값이 next 이면 (즉 누른 Pagination 이 다음 PageBlock 으로 넘어가는것이였을때)
         	selectedPage = next;											// 선택한 Page 값을 현재 마지막 Page 번호에 1을 더한 값으로 지정
         }
         if($id == "prev"){													// 이전 PageBlock 으로 넘어가는 Pagination 을 Click 했을때
         	selectedPage = prev;											// 선택한 Page 값을 현재의 첫번쨰 Page 번호에 1을 뺸값으로 지정
         }
         pagination2(count, selectedPage, id, tableId, dataList);		// 선택한 Page 번호가 Pagination 의 Click Event 발생으로 변경되었으므로 Pagination 변수들을 초기화하여 선택한 Page 번호를 기준으로 맞게끔 재구성한다.
         $(tableId+" > tbody").empty();										// Table 을 한번 비우고
			for(var i=0; i<pageSize; i++){									// Pagesize 만큼 반복하여 Data 를 출력
				if(num<1){								
					break;
				}
				var file = dataList[curPos++];
				$($(tableId).find("tbody")).append("<tr><td>"+(num--)+"</td><td>"+file.file_name+"</td><td>"+file.uploader+"</td><td>"
														+file.date+"</td><td>"+file.hit+"</td><td style=\"display:none;\">"+file.seq+"</td></tr>");
			}	
     });                                    
}