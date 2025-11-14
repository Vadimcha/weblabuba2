const API_URL = `${window.location.origin}${window.location.pathname.replace(/\/[^/]*$/, '')}/controller`;

const dotEl = document.getElementById('dot');
const resEl = document.getElementById('res');
const errorEls = document.getElementsByClassName('error');
const inputEls = document.getElementsByClassName('field');
const tableEl = document.getElementById('table');
const submitBtn = document.getElementById('submit_btn');
const plot = document.querySelector('.plot');
const plot_size = 300;   // размер картинки

const ranges = [[-5, 3], [-3, 5], [1, 5]]
const errorText = "Число должно быть от MIN до MAX";


const handleSubmit = (e) => {
  e.preventDefault();

  resetStyles();

  const rowValues = getData();
  if(!isDataValid(rowValues)) return;
  const values = rowValues.map(value => String(value));
  requestCalculations(values);
}
document.getElementById('form').addEventListener('submit', handleSubmit);

const getData = () => {
  return Array.from(inputEls).map(el => el.value);
}

const isDataValid = (values) => {
  const errors = [];
  values.map((value, i) => {
    if(!isValueValid(value, ranges[i][0], ranges[i][1])) {
      errors.push(
        errorText
          .replace('MIN', `${ranges[i][0]}`)
          .replace('MAX', `${ranges[i][1]}`)
      );
    } else errors.push('');
  });
  setErrors(errors);
  return errors.filter(el => el !== "").length === 0;
}

const isValueValid = (n, min, max) => {
  const numberRegex = /^-?\d+(\.\d+)?$/;

  if (!numberRegex.test(n)) {
    return false;
  }

  const num = Number(n);
  return num >= min && num <= max;
}

const requestCalculations = (values) => {
  const params = new URLSearchParams({
    x: values[0],
    y: values[1],
    r: values[2]
  }).toString();

  const url = `${API_URL}?${params}`;
  request(url, values);
}

const request = (url, values) => {
  toggleLoading();
  window.location.href = `controller?x=${values[0]}&y=${values[1]}&r=${values[2]}`;
  placeDot(values)
  toggleLoading()
}

const processResponse = (res) => {
  resetStyles();

  if(res.status === "get_table" || res.status === "reset_table") {
    renderTable(res.history);
  }
  else if(res.status === "error") {
    resEl.setAttribute('data-status', "error");
    resEl.innerText = res.error;
    renderTable(res.history);
  }
  else {
    resEl.setAttribute('data-status', res.history[0].hit_status ? "ok" : "error");
    resEl.innerText = res.history[0].res;
    placeDot([parseFloat(res.history[0].x), parseFloat(res.history[0].y), parseFloat(res.history[0].r)]);
    renderTable(res.history);
  }
}

const placeDot = (values) => {
  const center = plot_size / 2;
  const rpx = (plot_size - 85) / 2;  // R в пикселях
  const scale = rpx / values[2];    // пикселей на 1 единицу по оси R

  const px = center + values[0] * scale;
  const py = center + values[1] * scale;

  dotEl.style.display = 'block';
  dotEl.style.left = `${px}px`;
  dotEl.style.bottom = `${py}px`;
}

const resetStyles = () => {
  resEl.removeAttribute('data-status');
  resEl.innerText = '';
  dotEl.style.display = 'none';
  Array.from(errorEls).forEach(errorEl => errorEl.removeAttribute('data-error'));
}

const setErrors = (errors) => {
  errors.map((error, i) => {
    errorEls[i].setAttribute('data-error', error);
  })
}

const toggleLoading = () => {
  if (resEl.getAttribute('data-status') === 'loading')
    resEl.setAttribute('data-status', 'loading');
  else resEl.removeAttribute('data-status');
}

const renderTable = (history) => {
  const tbody = document.getElementById('tbody');
  tbody.innerHTML = '';

  tableEl.setAttribute('data-visible', 'true');
  history.forEach((row, index) => {
    const tr = document.createElement('tr');
    tr.innerHTML = `
        <td data-label="Запрос №">${history.length - index}</td>
        <td data-label="RunTime (нано сек)">${row.run_time}</td>
        <td data-label="Координата X">${row.x}</td>
        <td data-label="Координата Y">${row.y}</td>
        <td data-label="Значение R">${row.r}</td>
        <td data-label="Результат">${row.res}</td>
      `;
    tbody.appendChild(tr);
  });
}

const resetTable = () => {
  tableRequests("reset_table");
}
document.getElementById('clear').addEventListener('click', resetTable);

const tableRequests = (type) => {
  const req = {
    type,
  }
  request(req);
}
window.addEventListener("load", () => tableRequests("get_table"));

let intervalId = null;
const generateDot = () => {
  const btn = document.getElementById('generate_btn');

  if (intervalId !== null) {
    submitBtn.disabled = false;
    clearInterval(intervalId);
    intervalId = null;
    btn.innerText = 'Запустить генерацию';
    btn.style.backgroundColor = 'var(--color-inteface)';
    return;
  }

  intervalId = setInterval(() => {
    btn.innerText = 'Сгенерировать';
    submitBtn.disabled = true;
    btn.style.backgroundColor = 'var(--color-error)';

    const x_btn = Math.random() * (window.innerHeight - btn.offsetHeight);
    const y_btn = Math.random() * (window.innerWidth - btn.offsetWidth);
    btn.style.bottom = 'unset';
    btn.style.right = 'unset';
    btn.style.top = x_btn + 'px';
    btn.style.left = y_btn + 'px';

    const x = Math.random() * (ranges[0][1] - ranges[0][0]) + ranges[0][0];
    const y = Math.random() * (ranges[1][1] - ranges[1][0]) + ranges[1][0];
    const r = Math.random() * (ranges[2][1] - ranges[2][0]) + ranges[2][0];

    inputEls[0].value = x.toFixed(2);
    inputEls[1].value = y.toFixed(2);
    inputEls[2].value = r.toFixed(2);

    const xButtons = document.querySelectorAll('.x_buttons button');
    xButtons.forEach(b => b.classList.remove('selected'));
    const nearestX = Math.round(x);
    xButtons.forEach(b => {
      if (parseFloat(b.textContent) === nearestX) b.classList.add('selected');
    });

    const rRadios = document.querySelectorAll('.r_radio input[type="radio"]');
    rRadios.forEach(radio => {
      radio.checked = false;
      if (parseFloat(radio.value) === Math.round(r)) {
        radio.checked = true;
        inputEls[2].value = radio.value;
      }
    });

    placeDot([x, y, r]);
  }, 2000);
};
document.getElementById('generate_btn').addEventListener('click', generateDot);

// Инициализация кнопок for X
document.addEventListener("DOMContentLoaded", () => {
  const inputContainer = document.querySelector(".input");
  const inputField = inputContainer.querySelector("input");

  const buttonContainer = document.createElement("div");
  buttonContainer.classList.add("x_buttons");

  console.log(buttonContainer, inputField)
  inputContainer.insertBefore(buttonContainer, inputField);

  for (let i = ranges[0][0]; i <= ranges[0][1]; i++) {
    const btn = document.createElement("button");
    btn.textContent = i;
    btn.type = "button";

    btn.addEventListener("click", () => {
      buttonContainer.querySelectorAll("button").forEach(b => b.classList.remove("selected"));
      btn.classList.add("selected");
      inputField.value = i;
    });

    buttonContainer.appendChild(btn);
  }
});
// radio for Y
document.addEventListener("DOMContentLoaded", () => {
  const inputContainer = document.getElementsByClassName("input")[2];
  const inputField = inputContainer.querySelector("input");

  const buttonContainer = document.createElement("div");
  buttonContainer.classList.add("r_buttons");

  inputContainer.insertBefore(buttonContainer, inputField);

  for (let i = ranges[2][0]; i <= ranges[2][1]; i++) {
    const radioWrapper = document.createElement("label");
    radioWrapper.classList.add("r_radio");

    const radio = document.createElement("input");
    radio.type = "radio";
    radio.name = "r_choice";
    radio.value = i;

    const fakeCircle = document.createElement("span");
    fakeCircle.classList.add("custom_radio");
    fakeCircle.textContent = i;

    radio.addEventListener("change", () => {
      if (radio.checked) {
        inputField.value = i;
      }
    });

    radioWrapper.append(radio, fakeCircle);
    buttonContainer.appendChild(radioWrapper);
  }
});

const handlePlotClick = (event) => {
  resetStyles();
  const R = getData()[2];
  if(!isValueValid(R, ranges[2][0], ranges[2][1])) {
    setErrors([
      '',
      '',
      errorText
        .replace('MIN', ranges[2][0])
        .replace('MAX', ranges[2][1])
    ])
    return;
  }


  const rect = plot.getBoundingClientRect();
  const center = plot_size / 2;
  const rpx = (plot_size - 85) / 2;
  const scale = rpx / R;

  const x_click = event.clientX - rect.left;
  const y_click = event.clientY - rect.top;

  const x_val = (x_click - center) / scale;
  const y_val = (center - y_click) / scale;

  const values = [x_val, y_val, R].map(value => String(value));
  requestCalculations(values);
}
plot.addEventListener('click', handlePlotClick);