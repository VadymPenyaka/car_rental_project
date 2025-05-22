import React, { useState } from "react"
import { Button } from "../ui/button"
import { Input } from "../ui/input"
import { Label } from "../ui/label"
import { Card, CardContent, CardHeader, CardTitle, CardFooter } from "../ui/card"
import { sendRequest } from "@/lib/utils"
import { useNavigate } from "react-router"

import useSignIn from 'react-auth-kit/hooks/useSignIn'

export const LoginPage: React.FC = () => {
	const signIn = useSignIn()
	const navigate = useNavigate()
	const [isLogin, setIsLogin] = useState(true)

	// Shared state
	const [email, setEmail] = useState("")
	const [password, setPassword] = useState("")

	// Registration-specific state
	const [firstName, setFirstName] = useState("")
	const [sureName, setSureName] = useState("")
	const [phoneNumber, setPhoneNumber] = useState("")

	// Field-level error state
	const [errors, setErrors] = useState<{ [key: string]: string }>({})

	const PHONE_PATTERN = /^\+380\d{9}$/
	const PASSWORD_PATTERN = /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\S+$).{8,18}$/
	const EMAIL_PATTERN = /^[a-zA-Z0-9+&-]+(?:\.[a-zA-Z0-9_+&-]+)*@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,7}$/

	const handleLogin = async (e: React.FormEvent) => {
		e.preventDefault()
	
		const newErrors: { [key: string]: string } = {}
	
		if (!EMAIL_PATTERN.test(email)) {
			newErrors.email = "Invalid email format"
		}
	
		if (!PASSWORD_PATTERN.test(password)) {
			newErrors.password = "Password must be 8-18 characters long and include uppercase, lowercase, number, and special character."
		}
	
		if (Object.keys(newErrors).length > 0) {
			setErrors(newErrors)
			return
		}
	
		setErrors({}) // clear previous errors
	
		try {
			const response = await sendRequest({
				url: '/api/v1/auth/login',
				method: "POST",
				withCredentials: false,
				headers: {
					"Content-Type": "application/json",
				},
				data: JSON.stringify({ username: email, password }),
			})
			console.log("response: ", response);
			
	
			if (response?.status == 200) {
				console.log("1");
				
				signIn({
					auth: {
						token: response.data.token,
						type: 'Bearer',
					},
					refresh: response.data.token,
					userState: {
						role: response.data.role,
					}
				})
				console.log("2");
				navigate('/')
				console.log("3");
			}
		} catch (err: any) {
			const backendErrors: { [key: string]: string } = {}
	
			if (err.response?.data && Array.isArray(err.response.data)) {
				err.response.data.forEach((errorItem: any) => {
					if (errorItem.field && errorItem.defaultMessage) {
						backendErrors[errorItem.field] = errorItem.defaultMessage
					}
				})
			} else {
				backendErrors.general = "Login failed. Please check your credentials."
			}
	
			setErrors(backendErrors)
		}
	}
	

	const handleRegister = async (e: React.FormEvent) => {
		e.preventDefault()

		const newErrors: { [key: string]: string } = {}

		if (!EMAIL_PATTERN.test(email)) {
			newErrors.email = "Invalid email format"
		}

		if (!PHONE_PATTERN.test(phoneNumber)) {
			newErrors.phoneNumber = "Phone number must start with +380 and contain exactly 9 digits after"
		}

		if (!PASSWORD_PATTERN.test(password)) {
			newErrors.password = "Password must be 8-18 characters long and include uppercase, lowercase, number, and special character."
		}

		if (!firstName) {
			newErrors.firstName = "First name is required"
		}

		if (!sureName) {
			newErrors.sureName = "Surname is required"
		}

		if (Object.keys(newErrors).length > 0) {
			setErrors(newErrors)
			return
		}

		// If all is valid, clear errors
		setErrors({})

		try {
			const response = await sendRequest({
				url: "/api/v1/customers/register",
				method: "POST",
				withCredentials: false,
				headers: {
					"Content-Type": "application/json",
				},
				data: JSON.stringify({ email, firstName, sureName, phoneNumber, password }),
			})
			console.log("response: ", response);
			
			if (response.status === 201) {
				setIsLogin(true)
				alert("Account created successfully, now you can login")
			}
		} catch (err: any) {
			// Handle AxiosError
			const backendErrors: { [key: string]: string } = {}

			if (err.response?.data && Array.isArray(err.response.data)) {
				err.response.data.forEach((errorItem: any) => {
					if (errorItem.field && errorItem.defaultMessage) {
						backendErrors[errorItem.field] = errorItem.defaultMessage
					}
				})
			} else {
				console.error("Unexpected error format:", err)
				alert("Something went wrong. Please try again.")
			}

			setErrors(backendErrors)
		}
	}


	const inputClass = (field: string) => `${errors[field] ? "border-red-500" : ""}`

	const renderError = (field: string) =>
		errors[field] ? <p className="text-red-500 text-sm mt-1">{errors[field]}</p> : null

	return (
		<div className="min-h-screen flex items-center justify-center bg-white">
			<Card className="w-full max-w-sm">
				{isLogin ? (
					<>
						<CardHeader>
							<CardTitle className="text-center">Login</CardTitle>
						</CardHeader>
						<CardContent>
							<form className="space-y-4" onSubmit={handleLogin}>
								<div>
									<Label htmlFor="email">Email</Label>
									<Input
										type="email"
										id="email"
										placeholder="Enter your email"
										value={email}
										onChange={(e) => setEmail(e.target.value)}
										className={inputClass("email")}
										required
									/>
									{renderError("email")}
								</div>

								<div>
									<Label htmlFor="password">Password</Label>
									<Input
										type="password"
										id="password"
										placeholder="Enter a secure password"
										value={password}
										onChange={(e) => setPassword(e.target.value)}
										className={inputClass("password")}
										required
									/>
									{renderError("password")}
								</div>

								<Button type="submit" className="w-full bg-orange-500 hover:bg-orange-600">
									Login
								</Button>
							</form>
						</CardContent>
						<CardFooter className="text-center">
							<p className="text-sm text-gray-600">
								Don't have an account?{" "}
								<Button variant="link" onClick={() => setIsLogin(false)} className="text-orange-500">
									Register
								</Button>
							</p>
						</CardFooter>
					</>
				) : (
					<>
						<CardHeader>
							<CardTitle className="text-center">Register</CardTitle>
						</CardHeader>
						<CardContent>
							<form className="space-y-4" onSubmit={handleRegister}>
								<div>
									<Label htmlFor="name">Name</Label>
									<Input
										type="text"
										id="name"
										placeholder="Enter your name"
										value={firstName}
										onChange={(e) => setFirstName(e.target.value)}
										className={inputClass("firstName")}
										required
									/>
									{renderError("firstName")}
								</div>
								<div>
									<Label htmlFor="surname">Surname</Label>
									<Input
										type="text"
										id="surname"
										placeholder="Enter your surname"
										value={sureName}
										onChange={(e) => setSureName(e.target.value)}
										className={inputClass("sureName")}
										required
									/>
									{renderError("sureName")}
								</div>
								<div>
									<Label htmlFor="email">Email</Label>
									<Input
										type="email"
										id="email"
										placeholder="Enter your email"
										value={email}
										onChange={(e) => setEmail(e.target.value)}
										className={inputClass("email")}
										required
									/>
									{renderError("email")}
								</div>
								<div>
									<Label htmlFor="phone">Phone Number</Label>
									<Input
										type="text"
										id="phone"
										placeholder="Enter your phone number"
										value={phoneNumber}
										onChange={(e) => setPhoneNumber(e.target.value)}
										className={inputClass("phoneNumber")}
										required
									/>
									{renderError("phoneNumber")}
								</div>
								<div>
									<Label htmlFor="password">Password</Label>
									<Input
										type="password"
										id="password"
										placeholder="Enter a secure password"
										value={password}
										onChange={(e) => setPassword(e.target.value)}
										className={inputClass("password")}
										required
									/>
									{renderError("password")}
								</div>

								<Button type="submit" className="w-full bg-orange-500 hover:bg-orange-600">
									Register
								</Button>
							</form>
						</CardContent>
						<CardFooter className="text-center">
							<p className="text-sm text-gray-600">
								Already have an account?{" "}
								<Button variant="link" onClick={() => setIsLogin(true)} className="text-orange-500">
									Login
								</Button>
							</p>
						</CardFooter>
					</>
				)}
			</Card>
		</div>
	)
}
