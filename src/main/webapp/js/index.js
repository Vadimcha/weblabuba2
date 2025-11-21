const API_URL = `${window.location.origin}${window.location.pathname.replace(/\/[^/]*$/, '')}/api`;

const resEl = document.getElementById('res');
const errorEls = document.getElementsByClassName('error');
const inputEls = document.getElementsByClassName('field');
const tableEl = document.getElementById('table');
const submitBtn = document.getElementById('submit_btn');
const plot = document.querySelector('.plot');
const plot_size = 300;

const ranges = [[-5, 3], [-3, 5], [1, 5]];
const errorText = "Число должно быть от MIN до MAX";

// ======== Форма ========
const handleSubmit = (e) => {
  resetStyles();
  const values = getData();
  if (!isDataValid(values)) {
    e.preventDefault();
    drawPoints();
    return;
  }

  // Отправка формы на сервлет с редиректом на result.jsp
  // e.preventDefault();
  // const formData = new FormData(document.getElementById('form'));
  // const params = new URLSearchParams(formData).toString();
  // window.location.href = `${API_URL}?${params}`;
};
document.getElementById('form').addEventListener('submit', handleSubmit);

const getData = () => Array.from(inputEls).map(el => el.value);

const isDataValid = (values) => {
  const errors = [];
  values.map((value, i) => {
    if(!isValueValid(value, ranges[i][0], ranges[i][1])) {
      errors.push(errorText.replace('MIN', `${ranges[i][0]}`).replace('MAX', `${ranges[i][1]}`));
    } else errors.push('');
  });
  setErrors(errors);
  return errors.filter(el => el !== "").length === 0;
}

const isValueValid = (n, min, max) => {
  const numberRegex = /^-?\d+(\.\d+)?$/;
  if (!numberRegex.test(n)) return false;
  const num = Number(n);
  return num >= min && num <= max;
}

const calculate = (values) => {
  const params = new URLSearchParams({
    x: values[0],
    y: values[1],
    r: values[2],
    requestType: 'calculate',
    from: 'plot'
  }).toString();

  request(params);
};

const request = (params) => {
  fetch(`${API_URL}?${params}`, { method: 'GET', headers: { 'Accept': 'application/json' } })
    .then(response => response.json())
    .then(data => processResponse(data))
    .catch(err => {
      console.error(err);
      resEl.setAttribute('data-status', 'error');
      resEl.innerText = 'Ошибка при запросе к серверу';
    });
}

// ======== Обработка ответа API ========
const processResponse = (res) => {
  resetStyles();

  if(res.status === "get_table") {
    renderTable(res.history);
  } else if (res.status === "clear") {
    renderTable(res.history);
    tableEl.setAttribute('data-visible', "false")
    drawPoints();
  } else if(res.status === "error") {
    resEl.setAttribute('data-status', "error");
    resEl.innerText = res.error;
    renderTable(res.history);
  } else {
    const hit = res.history[0].hit_status;
    resEl.setAttribute('data-status', hit ? "ok" : "error");
    resEl.innerText = res.history[0].res;
    placeDot([parseFloat(res.history[0].x), parseFloat(res.history[0].y), parseFloat(res.history[0].r)]);
    renderTable(res.history);
  }
  drawPoints()
}

// ======== График ========
const placeDot = (values, color, isTemp = true) => {
  const plotBlock = document.querySelector(".plot_block");

  plotBlock.querySelectorAll(".temporary").forEach(dot => dot.remove());

  const center = plot_size / 2;
  const rpx = (plot_size - 85) / 2;
  const scale = rpx / values[2];

  const dot = document.createElement("div");
  dot.classList.add("plot-dot");

  if (isTemp) {
    dot.classList.add("temporary");
  }

  dot.style.backgroundColor = color;
  dot.style.left = `${center + values[0] * scale}px`;
  dot.style.bottom = `${center + values[1] * scale}px`;

  plotBlock.appendChild(dot);
};


// ======== Стили и ошибки ========
const resetStyles = () => {
  resEl.removeAttribute('data-status');
  resEl.innerText = '';
  Array.from(errorEls).forEach(el => el.removeAttribute('data-error'));
}

const setErrors = (errors) => {
  errors.forEach((error, i) => {
    errorEls[i].setAttribute('data-error', error);
  });
}

// ======== Таблица ========
const renderTable = (history) => {
  const tbody = document.getElementById('tbody');
  tbody.innerHTML = '';
  tableEl.setAttribute('data-visible', 'true');

  history.forEach((row, index) => {
    const tr = document.createElement('tr');
    tr.innerHTML = `
      <td>${history.length - index}</td>
      <td>${row.run_time}</td>
      <td>${row.x}</td>
      <td>${row.y}</td>
      <td>${row.r}</td>
      <td>${row.res}</td>
    `;
    tbody.appendChild(tr);
  });
}

const clear = () => {
  const params = new URLSearchParams({
    requestType: 'clear'
  }).toString();
  request(params);
};
document.getElementById('clear').addEventListener('click', clear);

// const getAll = () => {
//   const params = new URLSearchParams({
//     requestType: 'get-all'
//   }).toString();
//   request(params);
// }
// window.addEventListener("load", () => getAll());

// ======== Генерация случайной точки ========
let intervalId = null;
const generateDot = () => {
  const btn = document.getElementById('generate_btn');

  if(intervalId !== null) {
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

    const x = Math.random()*(ranges[0][1]-ranges[0][0])+ranges[0][0];
    const y = Math.random()*(ranges[1][1]-ranges[1][0])+ranges[1][0];
    const r = Math.random()*(ranges[2][1]-ranges[2][0])+ranges[2][0];

    inputEls[0].value = x.toFixed(2);
    inputEls[1].value = y.toFixed(2);
    inputEls[2].value = r.toFixed(2);

    placeDot([x, y, r]);
  }, 2000);
};
document.getElementById('generate_btn').addEventListener('click', generateDot);

// ======== Клик по графику ========
const handlePlotClick = (event) => {
  resetStyles();
  const R = getData()[2];
  if(!isValueValid(R, ranges[2][0], ranges[2][1])) {
    setErrors(['','','Значение R некорректно']);
    return;
  }

  const rect = plot.getBoundingClientRect();
  const center = plot_size/2;
  const scale = (plot_size-85)/2 / R;

  const x_val = (event.clientX - rect.left - center)/scale;
  const y_val = (center - (event.clientY - rect.top))/scale;

  calculate([x_val, y_val, R]);
}
plot.addEventListener('click', handlePlotClick);

const handleInputsChange = () => {
  placeDot(getData());
}
Array.from(inputEls).forEach(el => el.addEventListener('input', handleInputsChange));


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
      handleInputsChange();
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
        handleInputsChange();
      }
    });

    radioWrapper.append(radio, fakeCircle);
    buttonContainer.appendChild(radioWrapper);
  }
});

const drawPoints = () => {
  const rows = document.querySelectorAll("#tbody tr");

  rows.forEach(row => {
    const cells = row.querySelectorAll("td");

    const x = parseFloat(cells[2].textContent);
    const y = parseFloat(cells[3].textContent);
    const r = parseFloat(cells[4].textContent);
    const color = cells[5].textContent.trim() === "Вы попали" ? "springgreen" : "red";
    console.log("POINT", x, y, r, color)
    placeDot([ x, y, r], color, false);
  });
};

window.addEventListener("pageshow", () => {
  drawPoints();
});

window.addEventListener("popstate", () => {
  drawPoints();
});