import Browser from "../Browser";
import Banner from "./Banner";
import styles from './Dashboard.module.css';

const Dashboard = () => {
    return (
        <div className={styles.dashboard}>
            <Banner />
            <Browser />
        </div>
    )
}

export default Dashboard;