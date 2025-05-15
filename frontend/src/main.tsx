import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { createBrowserRouter, RouterProvider } from 'react-router'
import './globals.css'

import App from './App.tsx'
import { PageNotFound } from './components/PageNotFound'
import { LoginPage } from './components/LoginPage'
import { SearchPage } from './components/SearchPage'
import { CarPage } from './components/CarPage'


const router = createBrowserRouter([
	{
		path: '/',
		element: <App />,
		errorElement: <PageNotFound />
	},
	{
		path: '/login',
		element: <LoginPage />,
		errorElement: <PageNotFound />
	},
	{

		path: '/search',
		element: <SearchPage />,
		errorElement: <PageNotFound />
	},
	{
		path: '/cars/:carId',
		element: <CarPage />,
		errorElement: <PageNotFound />
	}
])

createRoot(document.getElementById('root')!).render(
	<StrictMode>
		<RouterProvider router={router} />
	</StrictMode>
)
