//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ Gbridge Application 에서 사용하는 Commons ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
//------------------------------------------------------
// Session 확인!!
//------------------------------------------------------
function checkSession(){
	$.ajax({
		url:"/admin/common/session",
		type:"get",
		success:function(result){
			if(result=="/admin"){
				alert("session 이 만기 되었습니다.\n로그인 후 이용해주세요");
				location.href="/admin";
			}
			var json = JSON.parse(result);
			var ACNT_ID = json.ACNT_ID;
			$("input[name='Uploader']").val(ACNT_ID);
		}
	});
}







//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ MYYZ Application 에서 사용하는 Commons ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ 
$(document).ready( function () {
    if(location.pathname.indexOf('login') != '1' && location.pathname.indexOf('dashboard') != '1') {
    	$('.datepicker').datepicker({
    		language: 'ko',
    		titleFormat:'yyyy년 mm월',
    		format: 'yyyy-mm-dd',
    		todayHighlight: true,
    		autoclose: true,
    		clearBtn: true
    	});

    	$('.datepicker-range').daterangepicker({
    	    // Predefined Ranges
    	    startDate: moment().subtract(0, 'days'),
    		//startDate: '2017-01-01',
    	    endDate: moment(),
    		locale:{
    			format: 'YYYY-MM-DD',
    			separator: " ~ ", 
    			daysOfWeek: ["일", "월", "화", "수", "목", "금", "토"], 
    			monthNames: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
    			applyLabel: "적용",
    	        cancelLabel: "취소",
    		},
    		showDropdowns: true
    	});
    }

    // 천단위 금액 표시 (소수점 3자리)
    Vue.filter('formatNumber', function (value) {
    	if(String(value).indexOf(".") > -1) {
    		return numeral(value).format('0,0.000');
    	}
    	else {
    		return numeral(value).format('0,0');
    	}
    });

    // 천단위 금액 표시 (소수점 1자리)
    Vue.filter('formatNumber1', function (value) {
    	if(String(value).indexOf(".") > -1) {
    		return numeral(value).format('0,0.0');
    	}
    	else {
    		return numeral(value).format('0,0');
    	}
    });

    // 천단위 금액 표시 (정수만)
    Vue.filter('formatNumber0', function (value) {
    	if(String(value).indexOf(".") > -1) {
    		return numeral(value).format('0,0');
    	}
    	else {
    		return numeral(value).format('0,0');
    	}
    });

    $(':button').click(function(evnet) {
        if($(this).data('dismiss')) {
            $('.' + $(this).data('dismiss')).hide();
        }
        else if($(this).data('show')) {
            $('#' + $(this).data('show')).show();
        }
    });

    $('#expire_login_bt').click(function() {
        location.href = '/login';
    });

    // 숫자만 입력되었는지 확인.
    $('.number').keyup(function(event){
        $(this).val($(this).val().replace(/[^0-9]/g,""));
    });
    $('.number').focusout(function(event){
        $(this).val($(this).val().replace(/[^0-9]/g,""));
    });

    // 숫자와 알파벳만 입력되었는지 확인.
    $('.alphabetAndNumber').keyup(function(event){
        $(this).val($(this).val().replace(/[^A-Za-z0-9]/g,""));
    });

    // 숫자와 Point만 입력되었는지 확인.
    $('.numberAndPoint').keyup(function(event){
        $(this).val($(this).val().replace(/[^0-9.]/g,""));
    });

    // 숫자와 알파벳과 하이픈(-)만 입력되었는지 확인.
    $('.alphabetAndNUmberAndHyphen').keyup(function(){
        $(this).val($(this).val().replace(/[^A-Za-z0-9-]/g,""));
    });

    // loadMask();

	removeHelpAll();
});

function removeHelpAll() {
	$(".form-group").find("input").keydown(function(){
        removeHelp.call(this);
    });

    $(".form-group").find("select").change(function(){
        removeHelp.call(this);
    });

    $(".form-group").find("textarea").keydown(function(){
        removeHelp.call(this);
    });

    $(".form-group").find(".datetpicker").focusout(function(){
        removeHelp.call(this);
    });
    $(".form-group").find("input").change(function(){
        removeHelp.call(this);
    });

    $(".form-group").find("file").click(function(){
        removeHelp.call(this);
    });

    $('#instance_list').click(function() {
        removeHelp.call(this);
    });

    $('.date').click(function() {
        removeHelp.call(this);
    });
}

var pattern = [];
pattern['num'] = /^[0-9]+$/;
pattern['numAndColon'] = /^[0-9:]+$/;
pattern['float'] = /^\d{1,4}(\.{1}[\d]?[\d])?$/;
pattern['engnum'] = /^[a-zA-Z0-9]^/;
pattern['email'] = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

// validation patterns
pattern['eng'] = /^[a-zA-Z]^/;
pattern['eng_f'] = /^[ａ-ｚＡ-Ｚ]^/;
pattern['passwd'] = /[^0-9a-zA-Z0-9!#$%&’*+/=?^_`{|}~.-]+/;
pattern['sp'] = /[^\u3041-\u309e\u309b-\u309c\u30a1-\u30fe\uff61-\uff9f\u4E00-\u9FA5\uF900-\uFA2Da-zA-Zａ-ｚＡ-Ｚ0-9０-９!#$%&’’'*+/=?^_`{|}~.｡。､、！＃＄％＆‘＇＊＋－／＝？＾＿｀｛｜}～．\s \r\n-]+/;

var pieOptions = {
    cutoutPercentage: 30,
    rotation: -0.5 * Math.PI,
    circumference: 2 * Math.PI,
    animation: {
        animateRotate: true,
        animateScale: false
    }
};

var barChartOptions = {
    barPercentage: 0.8,
    categoryPercentage: 0.8,
    responsive: true,
    legend: {
        position: 'top',
    },
    scales: {
        yAxes:[
            {
                ticks:{
                    beginAtZero:true
                }
            }
        ]
    }
};

function checkError(res) {
    if(res.response){
        if(res.response.status === 403) {
            $('#modal-expire').show();
            location.href = '/login';
        }
        else {
        	if(res.response.data == null || res.response.data == '') {
        		showAlert('경고', '('+res.response.status+') 처리 중 오류가 발생하였습니다. 잠시 후 다시 시도해 주세요', 'danger');
        	}
        	else {
        		showAlert('경고', '('+res.response.status+') ' + res.response.data.error, 'danger');
        	}
        }
    }
}

function extractTel(tel) {
    var telNum = [];
    if (tel === "" || tel === null) {
        return telNum;
    } else {
        telNum.push(tel.substr(0,3));
        if (tel.length === 11) {
            telNum.push(tel.substr(3,4));
            telNum.push(tel.substr(7,4));
        } else {
            telNum.push(tel.substr(3,3));
            telNum.push(tel.substr(6,4));
        }
    }
    return telNum;
}


function formatNumber(v1,v2){
    var str = new Array();
    if(v1 == null){
        v1 = 0;
    }
    v1 = String(v1);
    for(var i=1;i<=v1.length;i++){
        if(i % v2) str[v1.length-i] = v1.charAt(v1.length-i);
        else str[v1.length-i] = ','+v1.charAt(v1.length-i);
    }
    return str.join('').replace(/^,/,'');
}

function formatRate(v1,v2){
    return v1.toFixed(1);
}

function dateFormat(t, type) {
    if(t == undefined || t == '') {
    	return '';
    }

	var d = new Date(t);
    var returnDate = '';

    switch (type) {
        case 'date':
            var month = '' + (d.getMonth() + 1);
            var day = '' + d.getDate();
            var year = d.getFullYear();

            if (month.length < 2) month = '0' + month;
            if (day.length < 2) day = '0' + day;

            returnDate = [year, month, day].join('-');
            break;
        case 'hour':
            var hours = d.getHours().toString();
            if (hours.length < 2) hours = '0' + hours.toString();
            returnDate = hours;
            break;
        case 'min':
            var mins = d.getMinutes().toString();
            if (mins.length < 2) mins = '0' + mins.toString();
            returnDate = mins;
            break;
        case 'time':
            var hours = d.getHours().toString();
            if (hours.length < 2) hours = '0' + hours.toString();
            var mins = d.getMinutes().toString();
            if (mins.length < 2) mins = '0' + mins.toString();

            var time = hours + ':' + mins;
            returnDate = time;
            break;
    }

    return returnDate;
}

function searchObj(searchObj, searchKey, searchVal){
    var obj = $.grep(searchObj, function(obj){return eval('obj.' + searchKey) == searchVal;})[0];
    return obj;
}

function checkLength(inputObj, maxLength){
    var inputString = inputObj.val();
    if(inputString.length > maxLength){
        showAlert("경고","입력가능한 최대 글자수를 초과하였습니다");
        // inputObj.val(inputString.substring(0,maxLength));
    }

    return inputString.substring(0,maxLength);
}

function showAlert(title, msg, type, goFunc){
    $('#modal-alert').removeClass('modal-primary');
    $('#modal-alert').removeClass('modal-info');
    $('#modal-alert').removeClass('modal-warning');
    $('#modal-alert').removeClass('modal-danger');
    $('#modal-alert').removeClass('modal-success');

    $('#alert-title').html(title);
    $('#alert-msg').html(msg);

    var alertType = type == undefined ? "modal-primary" : "modal-" +type;
    $('#modal-alert').addClass(alertType);
    $('#modal-alert').show();

    $('#alert_bt').focus();

    $("#alert_bt").off('click').click(function(){
        $("#modal-alert").hide();
        if(!goFunc){
            return false;
        }
        goFunc();
    });
}

function showConfirm(title, msg, goFunc, type){

    $('#modal-confirm').removeClass('modal-primary');
    $('#modal-confirm').removeClass('modal-info');
    $('#modal-confirm').removeClass('modal-warning');
    $('#modal-confirm').removeClass('modal-danger');
    $('#modal-confirm').removeClass('modal-success');

    $('#confirm-title').html(title);
    $('#confirm-msg').html(msg);

    var alertType = type == undefined ? "modal-info" : "modal-" +type;
    $('#modal-confirm').addClass(alertType);
    $('#modal-confirm').show();

    $("#confirm_bt").off('click').click(function(){
        $("#modal-confirm").hide();
        if(!goFunc){
            return false;
        }
        goFunc();
    });
}

// type : success, info, warning, error
function showGrowl(m, t, d) {
	var dl = d == undefined ? 2000 : d;
	$.bootstrapGrowl(m, {type: t, delay: dl});
}

function replaceValiString(type, val){
    var returnVal = val;
    switch (type){
        case 'number':
            returnVal = val.replace(/[^0-9]/g,"");
            break;
        case 'alphaNum':
            returnVal = val.replace(/[^A-Za-z0-9]/g,"");
            break;
        case 'alphaNumHy':
            returnVal = val.replace(/[^A-Za-z0-9-]/g,"");
            break;
    }

    return returnVal;
}

//쿠키를 가져온다.
function getCookie(name) {
	var search = name + "=";
	if (document.cookie.length > 0) { // 쿠키가 설정되어 있다면
		offset = document.cookie.indexOf(search);
		if (offset != -1) { // 쿠키가 존재하면
			offset += search.length;
			// set index of beginning of value
			end = document.cookie.indexOf(";", offset);
			// 쿠키 값의 마지막 위치 인덱스 번호 설정
			if (end == -1)
				end = document.cookie.length;
			return unescape(document.cookie.substring(offset, end));
		}
	}
	return "";
}

// 쿠키를 저장한다.
function saveCookie(name, userId) {
	var expires = new Date();
	expires.setTime(expires.getTime() + 1000 * 3600 * 24 * 30); // 30일
	document.cookie = name + "=" + escape(userId) + "; path=/; expires=" + expires.toGMTString();
}

// 쿠키를 삭제한다.
function deleteCookie(name, userId) {
	var expires = new Date();  // current date & time
	expires.setTime(expires.getTime() - 1);
	document.cookie = name + "=" + escape(userId) + "; path=/; expires=" + expires.toGMTString();
}

function showHelp_org(objId,msg){
    var parentObj = $("#"+objId).parents(".form-group");
    if(parentObj){
        parentObj.addClass("has-error");
        parentObj.children(".help-block").html(msg);
        parentObj.children(".help-block").removeClass("hide");
        if(!$("#"+objId).hasClass("datepicker") && !$("#"+objId).hasClass("timepicker")){
            $("#"+objId).focus();
        }
    }

    parentObj.on("focus", function() {
        console.log('focus!!');
    });
}

function removeHelp_org() {
    if ($(this).parents(".form-group").children(".help-block")) {
        $(this).parents(".form-group").removeClass("has-error").children(".help-block").addClass("hide").html("");
    }
}

function showHelp(objId, msg){
	$field = $("#"+objId);
    if($field && $field.length > 0){
    	$field.css("border-color", "#dd4b39");
    	$field.css("box-shadow", "none");

        $field.siblings('.error-message').remove();
        $field.after('<span class="error-message has-error text-muted taxt-small text-red">'+msg+'</span>');
    }
    $parent = $field.parent();
    $parent.addClass("has-error");
}

function removeHelp() {
	$(this).css("border-color", "");
	$(this).css("box-shadow", "");
	$(this).siblings('.error-message').remove();

	$parent = $(this).parent();
    $parent.removeClass("has-error");
}

function clearHelp(obj) {
	$divObj = obj.children(".form-group").children(".has-error")
	$divObj.children(".form-control").css("border-color", "");
	$divObj.children(".form-control").css("box-shadow", "");
	$divObj.children(".error-message").remove();
	$divObj.removeClass("has-error");
}

function numberWithCommas(x) {
    x = parseInt(x);
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

function convertingData(unit, list) {
    if(unit == 'Bytes') {
        var convertingList = new Array();
        for(var i=0; i<list.length; i++) {
            var info = list[i];
            var floatData = parseFloat(info);
            var newFloatData = floatData /1024/1024/1024;
            convertingList.push(newFloatData);
        }
        return convertingList;
    } else {
        return list;
    }
}

function loadMask(){
    try{
        $("input[type=tel]").inputmask('9{12}');
        $(".number").inputmask('9{1,10}');
        $('.english-and-number').inputmask({mask: "*{1,*}"});
        console.log("load mask");
    } catch(e){
        console.log("Can not load mask");
    }
}

function formatChartDate(dateString) {
    var returnString = moment(dateString, 'YYYYMMDDhhmmss').format('MM/DD HH:mm');
    return returnString;
}

function formatNumber(value) {
	if(String(value).indexOf(".") > -1) {
		return numeral(value).format('0,0.000');
	}
	else {
		return numeral(value).format('0,0');
	}
}

// 공통코드 조회
function getCommonCodeValidValues(domainEnglishName, callback) {
  axios({
      method: 'get',
      url: '/api/common/code/valid/value/'+domainEnglishName
  }).then(function(response) {
  	callback(response.data);
  }).catch(function(reason){
      checkError(reason);
  });
}

// 직원목록
function getEmployeeList(callback) {
    axios({
        method: 'get',
        url: '/api/system/account/employee/searchlist'
    }).then(function(response) {
    	callback(response.data);
    }).catch(function(reason){
        checkError(reason);
    });
}

// 국가코드
function getNationList(callback) {
    axios({
        method: 'get',
        url: '/api/basic/code/nation/searchlist'
    }).then(function(response) {
    	callback(response.data.nationCodeList, response.data.nationNameList);
    }).catch(function(reason){
        checkError(reason);
    });
}

//국가코드
function getNationListAll(callback) {
    axios({
        method: 'get',
        url: '/api/basic/code/nation/searchlist/all'
    }).then(function(response) {
    	callback(response.data.nationCodeList, response.data.nationNameList);
    }).catch(function(reason){
        checkError(reason);
    });
}

//국가지역
function getNationRegion(nationCode, callback) {
    axios({
        method: 'get',
        url: '/api/common/nation/'+nationCode+'/region',
    }).then(function(response) {
    	callback(response.data);
    }).catch(function(reason){
        checkError(reason);
    });
}

//국가지역목록
function getNationRegionList(nationCode, callback) {
    axios({
        method: 'get',
        url: '/api/common/nation/'+nationCode+'/regionList'
    }).then(function(response) {
        callback(response.data);
    }).catch(function(reason){
        checkError(reason);
    });
}

// 증권사정보
function getSecurities(nationCode, callback) {
    axios({
        method: 'get',
        url: '/api/common/securities/'+nationCode,
    }).then(function(response) {
    	callback(response.data);
    }).catch(function(reason){
        checkError(reason);
    });
}

// 거래주체(회사)
function getTransactionSubjectList(callback) {
    axios({
        method: 'get',
        url: '/api/common/transaction/subject'
    }).then(function(response) {
        callback(response.data);
    }).catch(function(reason){
        checkError(reason);
    });
}

// 거래유형
function getTransactionTypeList(callback) {
    axios({
        method: 'get',
        url: '/api/common/transaction/type'
    }).then(function(response) {
        callback(response.data);
    }).catch(function(reason){
        checkError(reason);
    });
}

//거래유형(전체)
function getTransactionTypeListAll(callback) {
    axios({
        method: 'get',
        url: '/api/common/transaction/all'
    }).then(function(response) {
        callback(response.data);
    }).catch(function(reason){
        checkError(reason);
    });
}


//포트폴리오(전체)
function getPortfolioList(callback) {
    axios({
        method: 'get',
        url: '/api/basic/port/searchlist',
    }).then(function(response) {
        callback(response.data);
    }).catch(function(reason){
        checkError(reason);
    });
}

//포트폴리오(사용)
function getPortfolioListUseYn(callback) {
    axios({
        method: 'get',
        url: '/api/basic/port/searchlist/useYn',
    }).then(function(response) {
        callback(response.data.portfolioList);
    }).catch(function(reason){
        checkError(reason);
    });
}

//포트폴리오(현물/선물 구분 : A/F)
function getPortfolioTypeList(type, callback) {
    axios({
        method: 'get',
        url: '/api/basic/port/searchlist/' + type,
    }).then(function(response) {
        callback(response.data);
    }).catch(function(reason){
        checkError(reason);
    });
}

//포트폴리오(국가코드, 현물/선물 구분 : A/F)
function getPortfolioNationAndTypeList(nationCode, type, callback) {
    axios({
        method: 'get',
        url: '/api/common/portfolio/searchlist/' + nationCode + '/' + type,
    }).then(function(response) {
        callback(response.data);
    }).catch(function(reason){
        checkError(reason);
    });
}

//종목
function getIssueList(nationCode, callback) {
	if(nationCode == null || nationCode == '') {
		callback([]);
		return;
	}

    axios({
        method: 'get',
        url: '/api/basic/issue/manage/nopagelist',
		params: {
			nationCode: nationCode
		}
    }).then(function(response) {
        //callback(response.data.manageList);
    	callback(response.data.manageIssueList);
    }).catch(function(reason){
        checkError(reason);
    });
}

// 포트폴리오에 해당하는 국가코드목록 조회
function getPortfolioNationList(portfolioId, callback) {
	if (portfolioId == null || portfolioId == '') {
		callback([]);
		return;
	}
	
    axios({
        method: 'get',
        url: '/api/basic/code/nation/searchByPortfoliolist',
		params: {
			portfolioId: portfolioId
		}
    }).then(function(response) {
        //callback(response.data.manageList);
    	callback(response.data.portfolioNationList);
    }).catch(function(reason){
        checkError(reason);
    });
}


// 포트폴리오 종목
function getPortfolioIssueList(portfolioId, nationCode, callback) {
	if((portfolioId == null || portfolioId == '') || (nationCode == null || nationCode == '')) {
		callback([]);
		return;
	}

    axios({
        method: 'get',
        url: '/api/basic/port/issue/nopagelist',
		params: {
			portfolioId: portfolioId,
			nationCode: nationCode
		}
    }).then(function(response) {
		var list = response.data.issuePortList;
    	
		/*
    	if(list.length > 0) {
    		for(var x = 0 ; x < list.length ; x++) {
    			var info = list[x];
    			info.issueName = info.issueInfo.issueName;
    		}
    	}
    	*/
        callback(list);
    }).catch(function(reason){
        checkError(reason);
    });
}

// 선물상품
function getFuturesProductList(callback) {
    axios({
        method: 'get',
        url: '/api/trade/futures/product/searchlist'
    }).then(function(response) {
        callback(response.data);
    }).catch(function(reason){
        checkError(reason);
    });
}

function removeCommas(str) {
    while (str.search(",") >= 0) {
        str = (str + "").replace(',', '');
    }
    return str;
}

//업종 목록
function getIndustryList(nationCode, callback) {
	if(nationCode == null || nationCode == '') {
		callback([]);
		return;
	}

    axios({
        method: 'get',
        url: '/api/basic/code/industry/nopagelist',
		params: {
			nationCode: nationCode
		}
    }).then(function(response) {
        callback(response.data.industryList);
    }).catch(function(reason){
        checkError(reason);
    });
}

//업종 목록
function getIndustryListAll(nationCode, callback) {
	if(nationCode == null || nationCode == '') {
		callback([]);
		return;
	}

    axios({
        method: 'get',
        url: '/api/basic/code/industry/nopagelist/all',
		params: {
			nationCode: nationCode
		}
    }).then(function(response) {
        callback(response.data.industryList);
    }).catch(function(reason){
        checkError(reason);
    });
}

//통화 목록
function getCurrencyList(callback) {
    axios({
        method: 'get',
        url: '/api/basic/code/currency/searchlist'
    }).then(function(response) {
    	callback(response.data.currencyCodeList);
    }).catch(function(reason){
        checkError(reason);
    });
}

//통화 목록
function getCurrencyListAll(callback) {
    axios({
        method: 'get',
        url: '/api/basic/code/currency/searchlist/all'
    }).then(function(response) {
    	callback(response.data.currencyCodeList);
    }).catch(function(reason){
        checkError(reason);
    });
}

function setTwoNumberDecimal(event) {
    this.value = parseFloat(this.value).toFixed(2);
}

// 소수점 체크
function isNumberKey(evt) {
    var charCode = (evt.which) ? evt.which : event.keyCode;

    if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }

    // Textbox value       
    var _value = event.srcElement.value;

    // 소수점(.)이 두번 이상 나오지 못하게
    var _pattern0 = /^\d*[.]\d*$/; // 현재 value값에 소수점(.) 이 있으면 . 입력불가
    if (_pattern0.test(_value)) {
        if (charCode == 46) {
            return false;
        }
    }

    // 소수점 첫째자리까지만 입력가능
    var _pattern2 = /^\d*[.]\d{1}$/; // 현재 value값이 소수점 둘째짜리 숫자이면 더이상 입력 불가
    if (_pattern2.test(_value)) {
        alert("소수점 첫째자리까지만 입력가능합니다.");
        return false;
    }
    return true;
}