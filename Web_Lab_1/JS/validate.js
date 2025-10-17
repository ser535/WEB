export default class Validate
{
    x;
    y;
    r;
    constructor() {
        this.log = "";
    }
    check(x, y, r) {
        this.log = "";
        if (!this.checkForNull(x, y, r))
            return {
                allOk: false,
                log: "Заполните все поля формы"
            };
        return {
            allOk: this.checkX(x) && this.checkY(y) && this.checkR(r),
            log: this.log
        };
    }
    checkForNull(x, y, r){
        if (x && y && r)
            return true;
        else
            return false;
    }
    checkX(x){
        x = parseFloat(x);
        this.x = x;
        if ([-3, -2, -1, 0, 1, 2, 3, 4, 5].includes(x))
            return true;
        else {
            this.log = "X нужно выбрать"
            return false;
        }
    }

    checkY(y) {

        console.log("checkY получил:", y, typeof y);


        if (!/^(-?\d+(\.\d+)?)$/.test(y)){
            this.log = "Y должен содержать число"
            return false;
        }
        y = parseFloat(y);
        this.y = y;
        if (isNaN(y)) {
            this.log = "Y нужно ввести"
            return false;
        }
        if (-3 <= y && y <= 3)
            return true;
        else {
            this.log = "Значение Y должно быть от -3 до 3"
            return false;
        }
    }

    checkR(r) {
        r = parseFloat(r);
        this.r = r;
        if ([1, 1.5, 2, 2.5, 3].includes(r))
            return true;
        else {
            this.log = "R нужно выбрать"
            return false;
        }
    }

    getCoords(){
        return{
            x: this.x,
            y: this.y,
            r: this.r
        };
    }
}