import "./style.css";

const Forbidden = () => {
    const handleBack = () => {
        // Aqui você pode redirecionar para outra página, por exemplo:
        window.location.href = "/";
    };

    return (
        <div className="container-error">
            <div className="card-error">
                <h1 className="title"> 🚫 Forbidden </h1>

                <p className="text">
                    You don't have permission for access this page
                </p>

                <button className="btn-back" onClick={handleBack}>
                    Back
                </button>
            </div>
        </div>
    );
};

export default Forbidden;