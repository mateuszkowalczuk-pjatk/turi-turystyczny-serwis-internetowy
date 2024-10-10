import StaysText from "./StaysText";
import StaysPanels from "./StaysPanels";
import styles from './Stays.module.css';

const Stays = () => {
    return (
        <div className={styles.stays}>
            <StaysText />
            <StaysPanels />
        </div>
    )
}

export default Stays;