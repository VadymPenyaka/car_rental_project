export const RentalInfo = () => {
    return (
        <main className="w-full max-w-5xl mx-auto my-8">
            {/* Page Header */}
            <header className="container mx-auto px-4 py-8 text-center">
                <h1 className="text-3xl md:text-4xl font-extrabold leading-tight">
                    Rent a Car with <span className="text-orange-500">5Cars</span> — Fast, Smart, Flexible
                </h1>
                <p className="mt-4 text-gray-600 text-lg max-w-2xl mx-auto">
                    Forget about public transport or overpriced taxis—get full control over your time, route, and comfort.
                </p>
            </header>

            {/* AI Feature as an Article */}
            <article className="container mx-auto px-4 py-8">
                <section className="max-w-2xl mx-auto text-center space-y-4">
                    <h2 className="text-3xl md:text-4xl font-semibold">
                        Let <span className="text-orange-500">AI</span> Find the Right Car for You
                    </h2>
                    <p className="text-gray-600 text-lg">
                        Just <strong>tell us what you need</strong>, and our AI assistant will instantly suggest the most suitable cars.
                    </p>
                </section>
            </article>

            {/* Fleet Overview in a Section */}
            <section className="container mx-auto px-4 py-8">
                <h2 className="text-3xl md:text-4xl font-semibold text-center">
                    100+ Cars in Our Fleet
                </h2>
                <p className="mt-2 text-gray-600 text-lg text-center">
                    Choose from a wide variety of well-maintained, modern vehicles:
                </p>
                <ul className="mt-6 max-w-md mx-auto list-disc list-inside space-y-2 text-gray-600">
                    <li>Economy · Standard · Business · Premium</li>
                    <li>SUVs · Crossovers · Minivans</li>
                    <li>Manual or automatic transmission</li>
                    <li>Gasoline · Diesel · Electric</li>
                    <li>Equipped with climate control, parking sensors, and more</li>
                </ul>
            </section>

            {/* Why Choose Us as an Aside */}
            <aside className="container mx-auto px-4 py-8">
                <h2 className="text-3xl md:text-4xl font-semibold text-center">
                    Why Choose <span className="text-orange-500">5Cars</span>?
                </h2>
                <ol className="mt-6 max-w-md mx-auto list-decimal list-inside space-y-3 text-gray-600">
                    <li>Smart AI recommendations</li>
                    <li>Quick and easy booking</li>
                    <li>Transparent pricing</li>
                    <li>Wide vehicle selection</li>
                    <li>Trusted service</li>
                </ol>
            </aside>

            {/* Page Footer */}
            <footer className="container mx-auto px-4 py-8 text-center text-sm text-gray-500">
            <p className="mt-6 text-lg font-medium text-center">
                    Just say what you need, and we'll match you with the right car.
                </p>
            </footer>
        </main>
    )
}
