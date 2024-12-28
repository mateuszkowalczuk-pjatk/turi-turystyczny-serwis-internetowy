import NotFoundHeader from '../NotFoundHeader'
import NotFoundTitle from '../NotFoundTitle'
import NotFoundDescription from '../NotFoundDescription'
import NotFoundButton from '../NotFoundButton'
import styles from './NotFound.module.css'

const NotFound = () => {
    return (
        <div className={styles.notfound}>
            <div className={styles.content}>
                <NotFoundHeader />
                <NotFoundTitle />
                <NotFoundDescription />
                <NotFoundButton />
            </div>
        </div>
    )
}

export default NotFound
