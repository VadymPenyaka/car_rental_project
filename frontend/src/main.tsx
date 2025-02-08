import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { createBrowserRouter, RouterProvider } from 'react-router'
import App from './App.tsx'
import './globals.css'

import { PageNotFound } from './components/PageNotFound'
import { LoginPage } from './components/LoginPage'
import { Header } from './components/header/Header.tsx'

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
	}
])

createRoot(document.getElementById('root')!).render(
	<StrictMode>
		<Header />
		<RouterProvider router={router} />
	</StrictMode>,
)
