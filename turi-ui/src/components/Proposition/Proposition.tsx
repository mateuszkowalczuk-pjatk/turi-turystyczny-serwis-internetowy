import Stays from "./Stays";
import Attractions from "./Attractions";
import styles from './Proposition.module.css';

const Proposition = () => {
    return (
        <div className={styles.proposition}>
            <Stays />
            <Attractions />
        </div>
    )
}

export default Proposition;