//判断是否每区都有值
function allHasValue(cds) {
    var flag = 1, charsNum = 0;
    $.each(cds, function(k, v) {
        charsNum += v.length;
        if (v.length == 0) {
            flag = 0;
        }
    });
    return {
        flag: flag,
        charsNum: charsNum
    };
}

function isLegalCode(codes) {

            //这一段加上否则直选和值类玩法不选号也能添加
            if (allHasValue(codes)['charsNum'] == 0) {
                return {
                    singleNum: 0,
                    isDup: 0
                };
            }

            var singleNum = 0, isDup = 0, parts;
            switch (androidJs.getMethodName()) {
                case 'EXDXDS':
                case 'QEDXDS':
                    singleNum = codes[0].length * codes[1].length;
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
                case 'SXZX':    //三星直选 12,34,567
                case "ZSZX":
                case 'QSZX':    //前三直选
                case 'SXDXDS':
                case 'QSDXDS':
                case 'ZSDXDS':
                    singleNum = codes[0].length * codes[1].length * codes[2].length;
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
                case 'SXZS':    //三星组三
                case "ZSZS":
                case 'QSZS':
                    singleNum = codes[0].length * (codes[0].length - 1);
                    isDup = singleNum > 2 ? 1 : 0;
                    break;
                case 'SXZL':    //三星组六  1234
                case "ZSZL":
                case 'QSZL':
                    singleNum = codes[0].length * (codes[0].length - 1) * (codes[0].length - 2) / helper.factorial(3);
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
                case 'SXLX':    //三星连选 12345,123,58
                case "ZSLX":
                case 'QSLX':
                    //每区都必须有数字
                    if (allHasValue(codes)['flag'] == 0) {
                        return {
                            singleNum: 0,
                            isDup: 0
                        };
                    }

                    var $betNums3 = 0, $betNums2 = 0, $betNums1 = 0;
                    //算注数 后三注数+后二注数+后一注数
                    $betNums3 = codes[0].length * codes[1].length * codes[2].length;
                    $betNums2 = codes[1].length * codes[2].length;
                    $betNums1 = codes[2].length;
                    singleNum = $betNums3 + $betNums2 + $betNums1;
                    isDup = singleNum > 3 ? 1 : 0;
                    break;
                case 'SXBD':    //三星包点 一注可以有多个号码 不同号码之间要用_分隔 因为有大于9的结果
                case "ZSBD":
                case 'QSBD':
                    parts = codes[0].split('_');
                    $.each(parts, function(k, v) {
                        singleNum += helper.SXBD[v];
                    });
                    isDup = parts.length > 1 ? 1 : 0;
                    break;
                case 'SXHHZX':    //三星混合组选 仅支持单式手工录入 12,34,567
                case "ZSHHZX":
                case 'QSHHZX':    //前三混合组选 仅支持单式手工录入 12,34,567
                    singleNum = codes[0].length * codes[1].length * codes[2].length;
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
                case "RSHHZX"://任三混合组选
                     var oneLength=0;
                     var twoLength=0;
                     if(codes[1].length == 0){
                        twoLength=0;
                     }else{
                        twoLength = codes[1].split('_').length;
                     }
                     switch(codes[0].length){
                        case 3:
                            oneLength=1;
                            break;
                        case 4:
                            oneLength=4;
                            break;
                        case 5:
                            oneLength=10;
                            break;
                        default:
                            oneLength=0;
                            break;
                     }

                     singleNum=oneLength * twoLength;
                     isDup = singleNum > 1 ? 1 : 0;
                     break;
                case 'EXZX':    //二星直选 0123456789,0123456789
                case 'QEZX':
                    singleNum = codes[0].length * codes[1].length;
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
                case 'EXZUX':    //二星组选 0123456789
                case 'QEZUX':
                    singleNum = codes[0].length * (codes[0].length - 1) / 2;
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
                case 'EXLX':    //二星连选 0123456789,0123456789
                case 'QELX':
                    //每区都必须有数字
                    if (allHasValue(codes)['flag'] == 0) {
                        return {
                            singleNum: 0,
                            isDup: 0
                        };
                    }

                    //算注数 后二注数+后一注数
                    var $betNums2 = 0, $betNums1 = 0;
                    $betNums2 = codes[0].length * codes[1].length;
                    $betNums1 = codes[1].length;
                    singleNum = $betNums2 + $betNums1;
                    isDup = singleNum > 2 ? 1 : 0;
                    break;
                case 'EXBD':    //二星包点 一注可以有多个号码 不同号码之间要用_分隔 因为有大于9的结果
                case 'QEBD':
                    parts = codes[0].split('_');
                    $.each(parts, function(k, v) {
                        singleNum += helper.EXBD[v];
                    });
                    isDup = parts.length > 1 ? 1 : 0;
                    break;
                case 'YXZX':    //一星直选

                    singleNum = codes[0].length;
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
                case 'WXDW':    //五星定位
                    var n = 4; //5!
                    for (var i = 0; i < 5; i++) {
                        if(codes[i] != '-') {
                            singleNum += codes[i].length;
                        }
					}
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
                case 'SXDW':    //低频特有 三星定位
                    singleNum = codes[0].length + codes[1].length + codes[2].length;
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
                case 'EMBDW':   //三星二码不定位 一注仅限一组号码，如1,2，因为奖金本来就低，也为了判断方便
                case 'QSEMBDW': //新增前三二码
                case 'ZSEMBDW': //新增中三二码
                case 'SXEMBDW': //新增四星二码不定位
                case 'WXEMBDW': //新增五星二码不定位
                    singleNum = codes[0].length * (codes[0].length - 1) / 2;
                    isDup = 0;
                    break;
                case 'WXSMBDW': //新增五星三码不定位
                    singleNum = codes[0].length * (codes[0].length - 1) * (codes[0].length - 2) / 6;
                    isDup = 0;
                    break;
                case 'EXDXDS':    //二星大小单双 一注仅限一个号码 因为奖金本来就低
                case 'QEDXDS':  //低频3D特有 前二大小单双 一注仅限一个号码 因为奖金本来就低
                    singleNum = codes[0].length * codes[1].length == 1 ? 1 : 0;
                    isDup = 0;
                    break;
                case 'SXDXDS':    //三星大小单双 一注仅限一个号码 因为奖金本来就低
                    singleNum = codes[0].length * codes[1].length * codes[2].length == 1 ? 1 : 0;
                    isDup = 0;
                    break;
                case 'YMBDW':   //三星一码不定位 一注仅限一个号码，如1，因为奖金本来就低，也为了判断方便
                case 'ZSYMBDW': //新增中三一码不定位
                case 'SXYMBDW': //新增四星一码不定位
                case 'WXYMBDW': //新增五星一码不定位
                case 'QSYMBDW': //低频P3P5特有 前三一码不定位
                    singleNum = codes[0].length;
                    isDup = 0;
                    break;
                case 'SXHZ':    //三星和值 一注可以有多个号码 不同号码之间要用_分隔 因为有大于9的结果
                case "ZSHZ":
                case 'QSHZ':
                    parts = codes[0].split('_');
                    $.each(parts, function(k, v) {
                        singleNum += helper.SXHZ[v];
                    });
                    isDup = parts.length > 1 ? 1 : 0;
                    break;
                case 'EXHZ':    //二星和值 一注可以有多个号码 不同号码之间要用_分隔 因为有大于9的结果
                case 'QEHZ':
                    parts = codes[0].split('_');
                    $.each(parts, function(k, v) {
                        singleNum += helper.EXHZ[v];
                    });
                    isDup = parts.length > 1 ? 1 : 0;
                    break;
                case 'SXZXHZ':  //低频3D特有 组选和值
                case 'QSZXHZ':  //低频P3P5特有 组选和值
                    parts = codes[0].split('_');
                    $.each(parts, function(k, v) {
                        singleNum += helper.SXZXHZ[v];
                    });
                    isDup = parts.length > 1 ? 1 : 0;
                    break;
                case 'SIXZX':    //四星直选 12,34,567
                case 'QSIZX':    //前四直选
                    singleNum = codes[0].length * codes[1].length * codes[2].length * codes[3].length;
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
                case 'WXZX':    //五星直选
                    //算注数 相乘即可
                    singleNum = codes[0].length * codes[1].length * codes[2].length * codes[3].length * codes[4].length;
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
                case 'WXLX':    //五星连选
                    //每区都必须有数字
                    if (allHasValue(codes)['flag'] == 0) {
                        return {
                            singleNum: 0,
                            isDup: 0
                        };
                    }

                    var $betNums5 = 0, $betNums3 = 0, $betNums2 = 0, $betNums1 = 0;
                    //算注数 后三注数+后二注数+后一注数
                    $betNums5 = codes[0].length * codes[1].length * codes[2].length * codes[3].length * codes[4].length;
                    $betNums3 = codes[2].length * codes[3].length * codes[4].length;
                    $betNums2 = codes[3].length * codes[4].length;
                    $betNums1 = codes[4].length;
                    singleNum = $betNums5 + $betNums3 + $betNums2 + $betNums1;
                    isDup = singleNum > 4 ? 1 : 0;
                    break;

                    //========== sd11y ===========//
                case 'REZX':    //任二直选
                	var n = 4; //5!
                    for (var i = 0; i < 4; i++) {
                    	//如果注码不写'-'的话可以省略两个if判断,效率差不多
						if(codes[i] != '-') {
							for (var j = (i+1); j < 5; j++) {
								if(codes[j] != '-') {
									singleNum += codes[i].length * codes[j].length;
								}
							}
						}
					}
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
                case 'RSZX':    //任三直选		xxxxxx
                	for (var i = 0; i < 3; i++) {
                		if(codes[i] != '-') {
                			for (var j = (i+1); j < 4; j++) {
                				if(codes[j] != '-') {
                					for (var k = (j+1); k < 5; k++) {
                						if(codes[k] != '-') {
                							singleNum += codes[i].length * codes[j].length * codes[k].length;
                						}
                					}
                				}
                			}
                		}
                	}
                	isDup = singleNum > 1 ? 1 : 0;
                	break;
                case 'RSIZX':    //任四直选		xxxxxx
                	for (var i = 0; i < 2; i++) {
                		if(codes[i] != '-') {
                			for (var j = (i+1); j < 3; j++) {
                				if(codes[j] != '-') {
                					for (var k = (j+1); k < 4; k++) {
                						if(codes[k] != '-') {
                						    for (var l = (k+1); l < 5; l++) {
                						        if(codes[l] != '-'){
                						            singleNum += codes[i].length * codes[j].length * codes[k].length * codes[l].length;
                						        }
                						    }
                						}
                					}
                				}
                			}
                		}
                	}
                	isDup = singleNum > 1 ? 1 : 0;
                	break;
                case 'REZUX':
                    singleNum=C(codes[0].length,2) * C(codes[1].length,2);
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
                case 'RSZS':    //任三组三
                    singleNum = C(codes[0].length, 3) * C(codes[1].length, 2)*2;
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
                case 'RSZL':    //任三组六
                    singleNum = C(codes[0].length, 3) * C(codes[1].length, 3);
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
                case 'SDQSZX':  //前三直选 01_02_03_04,02_03,01_05
                    if (codes.length != 3) {
                        return {
                            singleNum: 0,
                            isDup: 0
                        };
                    }
                    var result = helper.expandLotto(codes);
                    singleNum = result.length;
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
                case 'SDQEZX':     //前二直选 二段 01_02_03_04,02_03
                    if (codes.length != 2) {
                        return {
                            singleNum: 0,
                            isDup: 0
                        };
                    }
                    var result = helper.expandLotto(codes);

                    singleNum = result.length;
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
                case 'SDQSZUX':     //前三组选 一段 01_02_03_04
                    parts = codes[0].split('_');
                    singleNum = parts.length * (parts.length - 1) * (parts.length - 2) / helper.factorial(3);
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
                case 'SDQEZUX':     //前二组选 一段 01_02_03_04_05_06_07_08_09_10_11
                    parts = codes[0].split('_');
                    singleNum = parts.length * (parts.length - 1) / 2;
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
                case 'SDRX1':     //任选1 一段 01_02_03_04_05_06_07_08_09_10_11
                case 'TMSX':    //特码生肖
                case 'ZTYX':    //正特一肖
                case 'TMWS':    //特码尾数
                case 'ZTWS':    //正特尾数
                case 'TMSB':    //特码色波
                case 'NIUNIU':
                case 'ZONGX':
                    parts = codes[0].split('_');
                    singleNum = parts.length;
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
                case 'SDRX2':     //任选2 一段 01_02_03_04_05_06_07_08_09_10_11
                case 'EELM':    //六合彩，二中二
                case 'ERLX':    //六合彩，二连肖
                    parts = codes[0].split('_');
                    singleNum = parts.length * (parts.length - 1) / 2;
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
                case 'SDRX3':     //任选3 一段 01_02_03_04_05_06_07_08_09_10_11
                case 'SSLM':    //六合彩，三中三
                case 'SELM':    //六合彩，三中二
                case 'SNLX':    //六合彩，三连肖
                    parts = codes[0].split('_');
                    singleNum = parts.length * (parts.length - 1) * (parts.length - 2) / 6;
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
                case 'SDRX4':     //任选4 一段 01_02_03_04_05_06_07_08_09_10_11
                case 'SILX':    //六合彩，四连肖
                case "SIELM":
                case "SISLM":
                case "SISILM":
                    parts = codes[0].split('_');
                    singleNum = parts.length * (parts.length - 1) * (parts.length - 2) * (parts.length - 3) / 24;
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
                case 'SDRX5':     //任选5 一段 01_02_03_04_05_06_07_08_09_10_11
                case 'ZTBZ5':
                    parts = codes[0].split('_');
                    singleNum = parts.length * (parts.length - 1) * (parts.length - 2) * (parts.length - 3) * (parts.length - 4) / 120;
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
                case 'SDRX6':     //任选6 一段 01_02_03_04_05_06_07_08_09_10_11
                case 'ZTBZ6':
                    parts = codes[0].split('_');
                    singleNum = parts.length * (parts.length - 1) * (parts.length - 2) * (parts.length - 3) * (parts.length - 4) * (parts.length - 5) / 720;
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
                case 'SDRX7':     //任选7 一段 01_02_03_04_05_06_07_08_09_10_11
                case 'ZTBZ7':
                    parts = codes[0].split('_');
                    singleNum = parts.length * (parts.length - 1) * (parts.length - 2) * (parts.length - 3) * (parts.length - 4) * (parts.length - 5) * (parts.length - 6) / 5040;
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
                case 'SDRX8':     //任选8 一段 01_02_03_04_05_06_07_08_09_10_11
                case 'ZTBZ8':
                    parts = codes[0].split('_');
                    singleNum = parts.length * (parts.length - 1) * (parts.length - 2) * (parts.length - 3) * (parts.length - 4) * (parts.length - 5) * (parts.length - 6) * (parts.length - 7) / 40320;
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
                case 'ZTBZ9':
                    parts = codes[0].split('_');
                    singleNum = C(parts.length,9);
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
                case 'ZTBZ10':
                    parts = codes[0].split('_');
                    singleNum = C(parts.length,10);
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
                case 'SDQSBDW':     //前3不定位胆 一段 01_02_03_04_05_06_07_08_09_10_11
                case 'TMZX':    //特码直选
                case 'ZTYM':    //正特一码
                case "ZMZX1":
                case "ZMZX2":
                case "ZMZX3":
                case "ZMZX4":
                case "ZMZX5":
                case "ZMZX6":
                    parts = codes[0].split('_');
                    singleNum = parts.length;
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
                case "ZMRX"://正码任选
                    parts = codes[1].split('_');
                    singleNum=codes[0].length * parts.length;
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
                case 'SDQSDWD':     //前3定位胆 01_02_03,04_05,06_07为一单 也可以只买一位，如'01_02_03,,'表示只买个位胆，没买的位留空
                    $.each(codes, function(k, v) {
                        if (v != '') {
                            //号码不得重复
                            parts = v.split('_');
                            singleNum += parts.length;  //注意是数组长度，所以前面必须判断v != ''
                        }
                    });
                    isDup = singleNum > 3 ? 1 : 0;
                    break;
                case 'SDDDS':     //0单5双:750.0000元 (1注) 5单0双:125.0000元 (6注)1单4双:25.0000元 (30注)4单1双:10.0000元 (75注)2单3双:5.0000元 (150注)3单2双:3.7000元 (200注)
                case 'SDCZW':     // 一次只能选一注
                    singleNum = 1;
                    isDup = 1;
                    break;

                case 'YFFS':    //趣味玩法,一帆风顺
                case 'HSCS':    //趣味玩法,好事成双
                case 'SXBX':    //趣味玩法,三星报喜
                case 'SJFC':    //趣味玩法,四季发财
                case 'TMDXDS': //特码大小单双
                case 'ZTHZDXDS':
                    singleNum = codes[0].length;    //传来的数据模式 13567
                    isDup = singleNum > 1 ? 1 : 0;
                    break;

                case 'ZUX120':    //组选120
                    if (codes[0].length > 4){
                        singleNum = codes[0].length === 5?1:(helper.factorial(codes[0].length) / (helper.factorial(codes[0].length - 5) * 120));
                    }
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
                case 'ZUX24':    //组选24
                case 'QSIZUX24':
                    if (codes[0].length > 3){
                        singleNum = helper.factorial(codes[0].length) / (helper.factorial(codes[0].length - 4) * 24);
                    }
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
                case 'ZUX6':    //组选6
                case 'QSIZUX6':
                    if (codes[0].length > 1){
                        singleNum = helper.factorial(codes[0].length) / (helper.factorial(codes[0].length - 2) * 2);
                    }
                    isDup = singleNum > 1 ? 1 : 0;
                    break;

                case 'ZUX10':    //组选10
                case 'ZUX5':    //组选5
                case 'ZUX4':    //组选4
                case 'QSIZUX4':
                    if (codes[0].length > 0 && codes[1].length > 0){
                        var compareNum = codes[1].length;
                        for (i = 0; i < codes[0].length; i++){
                            var tmp = compareNum;
                            if (codes[1].indexOf(codes[0].substr(i, 1)) > - 1){
                                tmp = compareNum - 1;
                            }
                            if (tmp > 0){
                                singleNum += helper.factorial(tmp) / helper.factorial(tmp - 1);
                            }
                        }
                    }
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
                case 'QSIZUX12': //前四组选12
                case 'ZUX20':    //组选20
                case 'ZUX12':    //组选12
                case 'QSIZUX12':
                    if (codes[0].length > 0 && codes[1].length > 1){
                        var compareNum = codes[1].length;
                        for (i = 0; i < codes[0].length; i++){
                            var tmp = compareNum;
                            if (codes[1].indexOf(codes[0].substr(i, 1)) > - 1){
                                tmp = compareNum - 1;
                            }
                            if (tmp > 1){
                                singleNum += helper.factorial(tmp) / (helper.factorial(tmp - 2) * 2);
                            }
                        }
                    }
                    isDup = singleNum > 1 ? 1 : 0;
                    break;

                case 'ZUX60':    //组选60
                    if (codes[0].length > 0 && codes[1].length > 2){
                        var compareNum = codes[1].length;
                        for (i = 0; i < codes[0].length; i++){
                            var tmp = compareNum;
                            if (codes[1].indexOf(codes[0].substr(i, 1)) > - 1){
                                tmp = compareNum - 1;
                            }
                            if (tmp > 2){
                                singleNum += helper.factorial(tmp) / (helper.factorial(tmp - 3) * 6);
                            }
                        }
                    }
                    isDup = singleNum > 1 ? 1 : 0;
                    break;

                case 'ZUX30':    //组选30
                    if (codes[0].length > 1 && codes[1].length > 0){
                        var compareNum = codes[0].length;
                        for (i = 0; i < codes[1].length; i++){
                            var tmp = compareNum;
                            if (codes[0].indexOf(codes[1].substr(i, 1)) > - 1){
                                tmp = compareNum - 1;
                            }
                            if (tmp > 1){
                                singleNum += helper.factorial(tmp) / (helper.factorial(tmp - 2) * 2);
                            }
                        }
                    }
                    isDup = singleNum > 1 ? 1 : 0;
                    break;

                    //江苏快三
                case 'JSETDX':  //二同号单选 2个号区 11_22,34
                    if (codes.length != 2) {
                        return {
                            singleNum: 0,
                            isDup: 0
                        };
                    }
                    var parts0 = codes[0].length ? codes[0].split('') : [];
                    var parts1 = codes[1].length ? codes[1].split('') : [];
                    var len0=parts0.length,len1=parts1.length;
                    var same=0;
                    for(var i=0;i<parts1.length;i++){
                        if(parts0.indexOf(parts1[i])!=-1)
                            same++;
                    }
                    singleNum=parts0.length*parts1.length-same;
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
                case 'JSETFX':  //二同复选 1个号区 11_22_33
                case 'CYBUC':
                case 'CYBIC':
                case 'JSDX':  //大小
                case 'JSDS':  //单双
                case 'JSYS':  //颜色
                    //parts = codes[0].split('_');
                    singleNum = codes[0].length;
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
             case 'PKBX':  //包选
             case "PKTH"://同花  PKTH
             case "PKSZ"://顺子 PKSZ
             case "PKTHS"://同花顺 PKTHS
                    singleNum = codes[0].length;
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
                case 'CEBUC':
                case 'CEBIC':
                    if(codes[0].length<2)
                        singleNum=0;
                    else
                        singleNum=C(codes[0].length,2);
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
                case 'CSBUC':
                case 'CSBIC':
                    if(codes[0].length<3)
                        singleNum=0;
                    else
                        singleNum=C(codes[0].length,3);
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
                case 'JSHZ':    //快三和值
                    parts = codes[0].split('_');
                    singleNum = parts.length;
                    isDup = parts.length > 1 ? 1 : 0;
                    break;
                case 'JSSTTX':    //快三   江苏三同号通选
                    //parts = codes[0].split('_');	//111_222_333_444_555_666
                    singleNum = 1;
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
                case 'JSSLTX'://快三三连号通选
                    singleNum = 1;
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
                case 'JSEBT':   //二不同号
                    var codesLen = codes[0].length;
                    singleNum = (codesLen - 1) * codesLen / 2;
                    isDup = codesLen > 2 ? 1 : 0;
                    break;
                case 'JSSTDX':   //三同号单选
                    //parts = codes[0].split('_');
                    singleNum = codes[0].length;
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
                case 'JSSBT':   //三不同号
                    var codesLen = codes[0].length;
                    singleNum = (codesLen - 1) * (codesLen - 2) * codesLen / 6;
                    isDup = codesLen > 3 ? 1 : 0;
                    break;

                    //快乐扑克
               case 'PKSZ'://顺子
                    parts = codes[0].split('_');
                    singleNum = parts.length;
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
               case 'PKBZ'://豹子
                   parts = codes[0].split('_');
                   singleNum = parts.length;
                   isDup = singleNum > 1 ? 1 : 0;
                   break;
               case 'PKDZ'://对子
                   parts = codes[0].split('_');
                   singleNum = parts.length;
                   isDup = singleNum > 1 ? 1 : 0;
                   break;
               case 'PKTH'://同花
                   parts = codes[0].split('_');
                   singleNum = parts.length;
                   isDup = singleNum > 1 ? 1 : 0;
                   break;
               case 'PKTHS'://同花顺
                   parts = codes[0].split('_');
                   singleNum = parts.length;
                   isDup = singleNum > 1 ? 1 : 0;
                   break;
               case 'PKBX'://包选
                   parts = codes[0].split('_');
                   singleNum = parts.length;
                   isDup = singleNum > 1 ? 1 : 0;
                   break;
               case 'PKRX1'://任选1
                   parts = codes[0].split('_');
                   singleNum = parts.length;
                   isDup = singleNum > 1 ? 1 : 0;
                   break;
               case 'PKRX2'://任选2
                   parts = codes[0].split('_');
                   singleNum = parts.length * (parts.length - 1) / 2;
                   isDup = singleNum > 1 ? 1 : 0;
                   break;
               case 'PKRX3'://任选3
                   parts = codes[0].split('_');
                   singleNum = parts.length * (parts.length - 1) * (parts.length - 2) / 6;
                   isDup = singleNum > 1 ? 1 : 0;
                   break;
               case 'PKRX4'://任选4
                   parts = codes[0].split('_');
                   singleNum = parts.length;
                   var codeNum = parts.length;
                   singleNum = codeNum * (codeNum - 1) * (codeNum - 2) * (codeNum - 3) / 24;
                   isDup = singleNum > 1 ? 1 : 0;
                   break;
               case 'PKRX5'://任选5
                   parts = codes[0].split('_');
                   var codeNum = parts.length;
                   singleNum = codeNum * (codeNum - 1) * (codeNum - 2) * (codeNum - 3) * (codeNum - 4) / 120;
                   isDup = singleNum > 1 ? 1 : 0;
                   break;
               case 'PKRX6'://任选6
                   parts = codes[0].split('_');
                   var codeNum = parts.length;
                   singleNum = codeNum * (codeNum - 1) * (codeNum - 2) * (codeNum - 3) * (codeNum - 4) * (codeNum - 5) / 720;
                   isDup = singleNum > 1 ? 1 : 0;
                   break;

               //PK10
               case 'LMGYH'://冠亚和
                    singleNum = codes[0].length;
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
               case 'LMMC'://名次
                    singleNum = codes[0].length+codes[1].length+codes[2].length+codes[3].length+codes[4].length+codes[5].length
                    +codes[6].length+codes[7].length+codes[8].length+codes[9].length;
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
               case 'LMLH'://龙虎
                    singleNum = codes[0].length+codes[1].length+codes[2].length+codes[3].length+codes[4].length;
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
               case 'GYHZ'://冠亚合值
               case 'CHZ'://猜和值
                    parts = codes[0].split('_');
                    $.each(parts, function (k, v) {
                        singleNum += helper.GYHZ[v];
                    });
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
               case 'QWMC'://猜前五
               case 'HWMC'://猜后五
               case 'CCW'://猜车位
                   for (var i = 0; i < codes.length; i++)
                   {
                       if(codes[i]!="")
                       {
                           parts=codes[i].split('_');
                           var codeNum = parts.length;
                           singleNum+=codeNum;
                       }
                   }
                   isDup = singleNum > 1 ? 1 : 0;
                   break;
               case 'QSMBDW'://前三名不定位
               case 'HSMBDW'://后三名不定位
                   parts=codes[0].split('_');
                   singleNum=parts.length;
                   isDup = singleNum > 1 ? 1 : 0;
                   break;
               case 'QEMZX'://前二名直选
               case 'HEMZX'://后二名直选
                   if (codes.length != 2)
                   {
                       return {
                           singleNum: 0,
                           isDup: 0
                       };
                   }
                   var result = helper.expandLotto(codes);
                   singleNum = result.length;
                   isDup = singleNum > 1 ? 1 : 0;
                   break;
               case 'QEMZUX'://前二名组选
               case 'HEMZUX'://后二名组选
                   parts = codes[0].split('_');
                   singleNum = parts.length * (parts.length - 1) / 2;
                   isDup = singleNum > 1 ? 1 : 0;
                   break;
               case 'QSMZX'://前三名直选
               case 'HSMZX'://后三名直选
                   if (codes.length != 3)
                   {
                       return {
                           singleNum: 0,
                           isDup: 0
                       };
                   }
                   var result = helper.expandLotto(codes);
                   singleNum = result.length;
                   isDup = singleNum > 1 ? 1 : 0;
                   break;
               case 'QEZUXBD': //前二包胆 QSZUXBD
               case 'EXZUXBD': //后二包胆 HSZUXBD
                    singleNum = codes[0].length * 9;
                    isDup = singleNum > 1 ? 1 : 0;
                   break;
               case 'QSZUXBD': //前三包胆 QSZUXBD
               case 'ZSZUXBD': //中三包胆 ZSZUXBD
               case 'HSZUXBD': //后三包胆 HSZUXBD
                    singleNum = codes[0].length * 54;
                    isDup = singleNum > 1 ? 1 : 0;
                   break;
               case 'QSMZL'://前三名组六
               case 'HSMZL'://后三名组六
                   parts = codes[0].split('_');
                   singleNum = parts.length * (parts.length - 1) * (parts.length - 2) / helper.factorial(3);
                   isDup = singleNum > 1 ? 1 : 0;
                   break;
               case 'QSIMZX'://前四名直选
               case 'HSIMZX'://后四名直选
                   if (codes.length != 4)
                   {
                       return {
                           singleNum: 0,
                           isDup: 0
                       };
                   }
                   var result = helper.expandLotto(codes);
                   singleNum = result.length;
                   isDup = singleNum > 1 ? 1 : 0;
                   break;
               case 'QSIMZUX'://前四名组选
               case 'HSIMZUX'://后四名组选
                   parts = codes[0].split('_');
                   singleNum = parts.length * (parts.length - 1) * (parts.length - 2) * (parts.length - 3) / helper.factorial(4);
                   isDup = singleNum > 1 ? 1 : 0;
                   break;
               case 'QWMZX'://前五名直选
               case 'HWMZX'://后五名直选
                   if (codes.length != 5)
                   {
                       return {
                           singleNum: 0,
                           isDup: 0
                       };
                   }
                   var result = helper.expandLotto(codes);
                   singleNum = result.length;
                   isDup = singleNum > 1 ? 1 : 0;
                   break;
               case 'QWMZUX'://前五名组选
               case 'HWMZUX'://后五名组选
                   parts = codes[0].split('_');
                   singleNum = parts.length * (parts.length - 1) * (parts.length - 2) * (parts.length - 3) * (parts.length - 4) / helper.factorial(5);
                   isDup = singleNum > 1 ? 1 : 0;
                   break;
               case 'JSPK'://竞速
                   singleNum=C(codes[0].length,2)*codes[1].length;
                   isDup = singleNum > 1 ? 1 : 0;
                   break;

               //胆拖
               case 'SDDTQEZUX':
                   singleNum =getDanTuo(codes,2);
                   isDup = singleNum > 1 ? 1 : 0;
                   break;
               case 'SDDTQSZUX':
                   singleNum =getDanTuo(codes,3);
                   isDup = singleNum > 1 ? 1 : 0;
                   break;
               case 'SDDTRX2':
                   singleNum =getDanTuo(codes,2);
                   isDup = singleNum > 1 ? 1 : 0;
                   break;
               case 'SDDTRX3':
                   singleNum =getDanTuo(codes,3);
                   isDup = singleNum > 1 ? 1 : 0;
                   break;
               case 'SDDTRX4':
                   singleNum =getDanTuo(codes,4);
                   isDup = singleNum > 1 ? 1 : 0;
                   break;
               case 'SDDTRX5':
                   singleNum =getDanTuo(codes,5);
                   isDup = singleNum > 1 ? 1 : 0;
                   break;
               case 'SDDTRX6':
                   singleNum =getDanTuo(codes,6);
                   isDup = singleNum > 1 ? 1 : 0;
                   break;
               case 'SDDTRX7':
                   singleNum =getDanTuo(codes,7);
                   isDup = singleNum > 1 ? 1 : 0;
                   break;
               case 'SDDTRX8':
                   singleNum =getDanTuo(codes,8);
                   isDup = singleNum > 1 ? 1 : 0;
                   break;

               case 'WXHZ'://五星和值
                   parts = codes[0].split('_');
                   $.each(parts, function (k, v) {
                       singleNum += helper.WXHZ[v];
                   });
                   isDup = singleNum > 1 ? 1 : 0;
                   break;
               case 'WXHZDXDS'://五星和值大小单双
                   singleNum=codes[0].length;
                   isDup = singleNum > 1 ? 1 : 0;
                   break;

               case 'REZUX':
                   singleNum=C(codes[0].length,2)*C(codes[1].length,2);
                   isDup = singleNum > 1 ? 1 : 0;
                   break;
               case 'RELHH':
                   singleNum=C(codes[0].length,2)*codes[1].length;
                   isDup = singleNum > 1 ? 1 : 0;
                   break;
               case 'RSIZX':
                   singleNum=C(codes[0].length,2)*codes[1].length;
                   isDup = singleNum > 1 ? 1 : 0;
                   break;
              case 'SDLX2'://乐选二
                    var oneLength = codes[0]=='' ? 0 : codes[0].split('_').length;
                    var twoLength = codes[1]=='' ? 0 : codes[1].split('_').length;
                    singleNum =(oneLength > 0 && twoLength > 0 )? (oneLength*twoLength*3) : 0;
                    isDup = singleNum > 1 ? 1 : 0;
                   break;
              case 'SDLX3'://乐选三
                   var oneLength = codes[0]=='' ? 0 : codes[0].split('_').length;
                   var twoLength = codes[1]=='' ? 0 : codes[1].split('_').length;
                   var threeLength = codes[2]=='' ? 0 : codes[2].split('_').length;
                   singleNum =(oneLength > 0 && twoLength > 0 && threeLength > 0)? (oneLength*twoLength*threeLength*3) : 0;;
                   isDup = singleNum > 1 ? 1 : 0;
                  break;
              case 'SDLX4'://乐选四
                  singleNum =getLeXuan4(codes);
                  isDup = singleNum > 1 ? 1 : 0;
                  break;
              case 'SDLX5'://乐选5
                    singleNum =getLeXuan5(codes);
                    isDup = singleNum > 1 ? 1 : 0;
                    break;
               default:
                   throw "unknown method2 " + ps.curMethod.name;
                   break;
            }

            return {
                singleNum: singleNum,
                isDup: isDup
            };
        };

    var getLeXuan4 = function (ballData) {
        var oneLength = ballData[0]=='' ? 0 : ballData[0].split('_').length;
        switch(oneLength)
        {
            case 4:
                   return 5;
                   break;
            case 5:
                   return 25;
                   break;
            case 6:
                  return 75;
                  break;
            case 7:
                  return 175;
                  break;
            case 8:
                  return 350;
                  break;
            case 9:
                 return 630;
                 break;
            case 10:
                  return 1050;
                  break;
            case 11:
                  return 1650;
                  break;
            default:
                  return 0;
        }
        return 0;
    };

    var getLeXuan5 = function (ballData) {
        var oneLength = ballData[0]=='' ? 0 : ballData[0].split('_').length;
        switch(oneLength)
        {
            case 5:
                   return 7;
                   break;
            case 6:
                  return 42;
                  break;
            case 7:
                  return 147;
                  break;
            case 8:
                  return 392;
                  break;
            case 9:
                 return 882;
                 break;
            case 10:
                  return 1764;
                  break;
            case 11:
                  return 3234;
                  break;
            default:
                  return 0;
        }
        return 0;
    };

    var getDanTuo = function (ballData, num) {
        var dmsLength = ballData[0]=='' ? 0 : ballData[0].split('_').length;
        var tmsLength = ballData[1]=='' ? 0 : ballData[1].split('_').length;
        return (dmsLength > 0 && tmsLength > 0 )? C(tmsLength, num - dmsLength) : 0;
    };
function jc(n){ var re=1; for(i=n;i>1;i--){re*=i;}return re; }
function C(m,n){ return m>=n ? jc(m)/(jc(n)*jc(m-n)) : 0; }
function array_unique(inputArr) {
    // http://kevin.vanzonneveld.net
    // +   original by: Carlos R. L. Rodrigues (http://www.jsfromhell.com)
    // +      input by: duncan
    // +   bugfixed by: Kevin van Zonneveld (http://kevin.vanzonneveld.net)
    // +   bugfixed by: Nate
    // +      input by: Brett Zamir (http://brett-zamir.me)
    // +   bugfixed by: Kevin van Zonneveld (http://kevin.vanzonneveld.net)
    // +   improved by: Michael Grier
    // +   bugfixed by: Brett Zamir (http://brett-zamir.me)
    // %          note 1: The second argument, sort_flags is not implemented;
    // %          note 1: also should be sorted (asort?) first according to docs
    // *     example 1: array_unique(['Kevin','Kevin','van','Zonneveld','Kevin']);
    // *     returns 1: {0: 'Kevin', 2: 'van', 3: 'Zonneveld'}
    // *     example 2: array_unique({'a': 'green', 0: 'red', 'b': 'green', 1: 'blue', 2: 'red'});
    // *     returns 2: {a: 'green', 0: 'red', 1: 'blue'}
    var key = '',
            tmp_arr2 = {}, val = '', tmp_arr3 = [];

    var __array_search = function(needle, haystack) {
        var fkey = '';
        for (fkey in haystack) {
            if (haystack.hasOwnProperty(fkey)) {
                if ((haystack[fkey] + '') === (needle + '')) {
                    return fkey;
                }
            }
        }
        return false;
    };

    for (key in inputArr) {
        if (inputArr.hasOwnProperty(key)) {
            val = inputArr[key];
            if (false === __array_search(val, tmp_arr2)) {
                tmp_arr2[key] = val;
                tmp_arr3.push(val);
            }
        }
    }
    //return tmp_arr2;  //返回对象
    return tmp_arr3;  //返回数组
}

var helper = {
            SXBD: {
                0: 1,
                1: 1,
                2: 2,
                3: 3,
                4: 4,
                5: 5,
                6: 7,
                7: 8,
                8: 10,
                9: 12,
                10: 13,
                11: 14,
                12: 15,
                13: 15,
                14: 15,
                15: 15,
                16: 14,
                17: 13,
                18: 12,
                19: 10,
                20: 8,
                21: 7,
                22: 5,
                23: 4,
                24: 3,
                25: 2,
                26: 1,
                27: 1
            },
            EXBD: {
                0: 1,
                1: 1,
                2: 2,
                3: 2,
                4: 3,
                5: 3,
                6: 4,
                7: 4,
                8: 5,
                9: 5,
                10: 5,
                11: 4,
                12: 4,
                13: 3,
                14: 3,
                15: 2,
                16: 2,
                17: 1,
                18: 1
            },
            SXHZ: {
                0: 1,
                1: 3,
                2: 6,
                3: 10,
                4: 15,
                5: 21,
                6: 28,
                7: 36,
                8: 45,
                9: 55,
                10: 63,
                11: 69,
                12: 73,
                13: 75,
                14: 75,
                15: 73,
                16: 69,
                17: 63,
                18: 55,
                19: 45,
                20: 36,
                21: 28,
                22: 21,
                23: 15,
                24: 10,
                25: 6,
                26: 3,
                27: 1
            },
            EXHZ: {
                0: 1,
                1: 2,
                2: 3,
                3: 4,
                4: 5,
                5: 6,
                6: 7,
                7: 8,
                8: 9,
                9: 10,
                10: 9,
                11: 8,
                12: 7,
                13: 6,
                14: 5,
                15: 4,
                16: 3,
                17: 2,
                18: 1
            },
            SXZXHZ: {
                1: 1,
                2: 2,
                3: 2,
                4: 4,
                5: 5,
                6: 6,
                7: 8,
                8: 10,
                9: 11,
                10: 13,
                11: 14,
                12: 14,
                13: 15,
                14: 15,
                15: 14,
                16: 14,
                17: 13,
                18: 11,
                19: 10,
                20: 8,
                21: 6,
                22: 5,
                23: 4,
                24: 2,
                25: 2,
                26: 1
            },
            //冠亚和值
            GYHZ: {
                3: 2,
                4: 2,
                5: 4,
                6: 4,
                7: 6,
                8: 6,
                9: 8,
                10: 8,
                11: 10,
                12: 8,
                13: 8,
                14: 6,
                15: 6,
                16: 4,
                17: 4,
                18: 2,
                19: 2
            },
            //五星和值
            WXHZ: {
                0: 1,
                1: 5,
                2: 15,
                3: 35,
                4: 70,
                5: 126,
                6: 210,
                7: 330,
                8: 495,
                9: 715,
                10: 996,
                11: 1340,
                12: 1745,
                13: 2205,
                14: 2710,
                15: 3246,
                16: 3795,
                17: 4335,
                18: 4840,
                19: 5280,
                20: 5631,
                21: 5875,
                22: 6000,
                23: 6000,
                24: 5875,
                25: 5631,
                26: 5280,
                27: 4840,
                28: 4335,
                29: 3795,
                30: 3246,
                31: 2710,
                32: 2205,
                33: 1745,
                34: 1340,
                35: 996,
                36: 715,
                37: 495,
                38: 330,
                39: 210,
                40: 126,
                41: 70,
                42: 35,
                43: 15,
                44: 5,
                45: 1
            },
            pokerNumMaps: {
                A:1,
                2:2,
                3:3,
                4:4,
                5:5,
                6:6,
                7:7,
                8:8,
                9:9,
                T:10,
                J:11,
                Q:12,
                K:13
            },
            factorial: function(n) {
                if (n <= 1) {
                    return 1
                } else {
                    return n * helper.factorial(n - 1)
                }
            },
            /**
             * 提取issue的期号 By Davy
             * issue 有如下类型：20150403-001	20150615-01		2015040
             * 逻辑：有'-'的取其后所有字符,没有的取最后三位
             */
            getNumByIssue: function(issue) {
            	if(issue.length == 0) {
            		return false;
            	}
                var pos = issue.indexOf("-");
                if (pos != -1) {
                    return issue.substr(pos+1);
                } else {
                    return issue.substr(issue.length-3);
                }
            },
            expandLotto: function($nums) {
                var result = [];
                var tempVars = [];
                var oneAreaIsEmpty = 0;
                $.each($nums,
                        function(k, v) {
                            if ($.trim(v) == "") {
                                oneAreaIsEmpty = 1;
                                return
                            }
                            var tmp = v.split("_");
                            tmp.sort();
                            tempVars.push(tmp);
                        });
                if (oneAreaIsEmpty) {
                    return [];
                }
                var i, j, k, l, m;
                switch ($nums.length) {
                    case 2:
                        for (i = 0; i < tempVars[0].length; i++) {
                            for (j = 0; j < tempVars[1].length; j++) {
                                result.push(tempVars[0][i] + " " + tempVars[1][j])
                            }
                        }
                        break;
                    case 3:
                        for (i = 0; i < tempVars[0].length; i++) {
                            for (j = 0; j < tempVars[1].length; j++) {
                                for (k = 0; k < tempVars[2].length; k++) {
                                    result.push(tempVars[0][i] + " " + tempVars[1][j] + " " + tempVars[2][k])
                                }
                            }
                        }
                        break;
                    case 4:
                        for (i = 0; i < tempVars[0].length; i++) {
                            for (j = 0; j < tempVars[1].length; j++) {
                                for (k = 0; k < tempVars[2].length; k++) {
                                    for (l = 0; l < tempVars[3].length; l++) {
                                        result.push(tempVars[0][i] + " " + tempVars[1][j] + " " + tempVars[2][k] + " " + tempVars[3][l])
                                    }
                                }
                            }
                        }
                        break;
                    case 5:
                        for (i = 0; i < tempVars[0].length; i++) {
                            for (j = 0; j < tempVars[1].length; j++) {
                                for (k = 0; k < tempVars[2].length; k++) {
                                    for (l = 0; l < tempVars[3].length; l++) {
                                        for (m = 0; m < tempVars[4].length; m++) {
                                            result.push(tempVars[0][i] + " " + tempVars[1][j] + " " + tempVars[2][k] + " " + tempVars[3][l] + " " + tempVars[4][m])
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    default:
                        throw "unkown expand";
                        break;
                }
                var $finalResult = [];
                $.each(result,
                        function(k, v) {
                            var $parts = v.split(" ");
                            var tmp = array_unique($parts);
                            if (tmp.length == $parts.length) {
                                $finalResult.push(v)
                            }
                        });
                return $finalResult;
            }
        };

function calculate() {
    var data = androidJs.getData();
    console.log(data);
    if (typeof data == "undefined") {
        console.log("androidJs.getData() == null");
        return;
    }
    data = JSON.parse(data);
    var result = isLegalCode(data);
    androidJs.result(result.singleNum, result.isDup);
}