import { BrowserRouter as Router, Route } from 'react-router-dom';
import './App.css';
import Main from './components/Main';
import Navbar from './components/Navbar';
import SurveyCreation from './components/surveyCreation/SurveyCreation';
import SurveyResponse from './components/surveyResponse/SurveyResponse';
import UserPage from './components/userPage/UserPage';
import ResultsPage from './components/surveyResults/Results';
import jwt from 'jwt-decode';

function App() {
	const isTokenExpired = () => {
		if (localStorage.length > 0) {
			let expiration = new Date(jwt(localStorage.getItem('token')).exp * 1000);
			let now = new Date();
			return now > expiration;
		}
	};

	if (isTokenExpired()) {
		localStorage.clear();
	}
	return (
		<Router>
			<div className='App'>
				<Navbar />
				<Route path='/' exact component={Main} />
				<Route path='/create' component={SurveyCreation} />
				<Route path='/respond/:surveyId' component={SurveyResponse} />
				<Route path='/user' component={UserPage} />
				<Route path='/results/:surveyId' component={ResultsPage} />
			</div>
		</Router>
	);
}

export default App;
