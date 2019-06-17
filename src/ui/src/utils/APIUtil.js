import axios from 'axios'

export const get = (url, params, successHanler, errorHandler = () => { }) => {
    axios.get(url, { params, withCredentials: true })
        .then(response => successHanler(response))
        .catch(error => errorHandler(error));
}


export const post = (url, data, successHanler, errorHandler = () => { }) => {
    axios.post(url, data, { withCredentials: true })
        .then(response => successHanler(response))
        .catch(error => errorHandler(error))
}

export const msg = (error) => {
    if (error.response.data.errors) {
        // If errors exsists, it means a validation exception may occured.
        return error.response.data.errors[0].defaultMessage;
    }
    return error.response.data.message;
}